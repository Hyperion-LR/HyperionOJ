package com.hyperionoj.page.contest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.cache.Cache;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.page.*;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.contest.dao.mapper.ContestProblemMapper;
import com.hyperionoj.page.contest.dao.mapper.ContestSubmitMapper;
import com.hyperionoj.page.contest.dao.mapper.ContestUserMapper;
import com.hyperionoj.page.contest.dao.pojo.ContestProblem;
import com.hyperionoj.page.contest.dao.pojo.ContestSubmit;
import com.hyperionoj.page.contest.dao.pojo.ContestUser;
import com.hyperionoj.page.contest.service.ContestUserService;
import com.hyperionoj.page.problem.service.ProblemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gui
 * @date 2021/12/11
 */
@Service
public class ContestUserServiceImpl implements ContestUserService {

    @Resource
    private ContestUserMapper contestUserMapper;

    @Resource
    private OSSClients ossClients;

    @Resource
    private ProblemService problemService;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private ContestSubmitMapper contestSubmitMapper;

    /**
     * 王比赛添加用户
     *
     * @param contestVo 比赛信息
     * @return 用户信息
     */
    @Override
    public SysUserVo addUserToContest(ContestVo contestVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        ContestUser contestUser = new ContestUser();
        contestUser.setUserId(sysUser.getId());
        contestUser.setContestsId(Long.valueOf(contestVo.getId()));
        contestUserMapper.insert(contestUser);
        return SysUserVo.userToVo(sysUser);
    }

    /**
     * 向比赛删除用户
     *
     * @param contestVo 向比赛删除用户的参数
     */
    @Override
    public void deleteUser(ContestVo contestVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        LambdaQueryWrapper<ContestUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContestUser::getContestsId, contestVo.getId());
        queryWrapper.eq(ContestUser::getUserId, sysUser.getId());
        contestUserMapper.delete(queryWrapper);
    }

    @Override
    public List<SysUserVo> findProblemsByContestId(Long contestId, PageParams params) {
        Page<ContestUser> page = new Page<>(params.getPage(), params.getPageSize());
        QueryWrapper<ContestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contests_id", contestId);
        Page<ContestUser> contestUserPage = contestUserMapper.selectPage(page, queryWrapper);
        return toUserVoList(contestUserPage.getRecords());
    }

    private List<SysUserVo> toUserVoList(List<ContestUser> contestUsers) {
        ArrayList<SysUserVo> sysUserVos = new ArrayList<>();
        for (ContestUser contestUser : contestUsers) {
            sysUserVos.add(SysUserVo.userToVo(ossClients.findUserById(contestUser.getUserId().toString()).getData()));
        }
        return sysUserVos;
    }

    /**
     * 提交代码
     *
     * @param id       比赛id
     * @param submitVo 提交情况
     * @return 结果
     */
    @Override
    public Object submit(Long id, SubmitVo submitVo) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        RunResult result = (RunResult) problemService.submit(submitVo);
        ContestSubmit contestSubmit = new ContestSubmit();
        contestSubmit.setContestsId(id);
        contestSubmit.setProblemId(Long.valueOf(submitVo.getProblemId()));
        contestSubmit.setRunTime(result.getRunTime());
        contestSubmit.setRunMemory(result.getRunMemory());
        contestSubmit.setCodeLang(submitVo.getCodeLang());
        contestSubmit.setAuthorId(sysUser.getId());
        if ("AC".equals(result.getVerdict())) {
            contestSubmit.setStatus(1);
        } else {
            contestSubmit.setStatus(0);
        }
        contestSubmitMapper.insert(contestSubmit);
        LambdaQueryWrapper<ContestProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContestProblem::getContestsId, id);
        queryWrapper.eq(ContestProblem::getProblemId, submitVo.getProblemId());
        queryWrapper.last(" limit 1");
        ContestProblem contestProblem = contestProblemMapper.selectOne(queryWrapper);
        LambdaUpdateWrapper<ContestProblem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ContestProblem::getContestsId, id);
        updateWrapper.eq(ContestProblem::getProblemId, submitVo.getProblemId());
        updateWrapper.set(ContestProblem::getSubmitCount, contestProblem.getSubmitCount() + 1);
        if ("AC".equals(result.getVerdict())) {
            updateWrapper.set(ContestProblem::getAcCount, contestProblem.getAcCount() + 1);
        }
        contestProblemMapper.update(null, updateWrapper);
        return result;
    }

    /**
     * 排行榜
     *
     * @param contestId 比赛id
     * @return 排行榜单
     */
    @Override
    @Cache()
    public List<RankVo> rank(Long contestId) {
        List<RankVo> rank = contestSubmitMapper.rank(contestId);
        for (int i = 1; i <= rank.size(); ++i) {
            RankVo rankVo = rank.get(i - 1);
            rankVo.setRank(i);
            rankVo.setUsername(ossClients.findUserById(rankVo.getAuthorId().toString()).getData().getUsername());
            rank.set(i - 1, rankVo);
        }
        return rank;
    }

}
