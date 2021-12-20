package com.hyperionoj.page.contest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.SysUserVo;
import com.hyperionoj.page.common.vo.params.PageParams;
import com.hyperionoj.page.contest.dao.mapper.ContestUserMapper;
import com.hyperionoj.page.contest.dao.pojo.ContestUser;
import com.hyperionoj.page.contest.service.ContestUserService;
import com.hyperionoj.page.contest.vo.ContestVo;
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


}
