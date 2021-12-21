package com.hyperionoj.page.contest.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.vo.page.ContestVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.contest.dao.mapper.ContestMapper;
import com.hyperionoj.page.contest.dao.mapper.ContestProblemMapper;
import com.hyperionoj.page.contest.dao.pojo.Contest;
import com.hyperionoj.page.contest.dao.pojo.ContestProblem;
import com.hyperionoj.page.contest.service.ContestService;
import com.hyperionoj.page.problem.service.ProblemService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.hyperionoj.common.constants.Constants.SALT;

/**
 * @author Gui
 * @date 2021/12/11
 */
@Service
public class ContestServiceImpl implements ContestService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private ProblemService problemService;

    /**
     * 创建比赛
     *
     * @param contestVo 创建比赛参数
     * @return 比赛详情
     */
    @Override
    public ContestVo createContest(ContestVo contestVo) throws ParseException {
        Contest newContest = voToContest(contestVo);
        contestMapper.insert(newContest);
        contestVo.setId(newContest.getId().toString());
        return contestVo;
    }

    /**
     * 修改比赛
     *
     * @param contestVo 修改比赛参数
     */
    @Override
    public ContestVo update(ContestVo contestVo) {
        LambdaUpdateWrapper<Contest> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Contest::getId, contestVo.getId());
        updateWrapper.set(Contest::getTitle, contestVo.getTitle());
        updateWrapper.set(Contest::getStartTime, contestVo.getStartTime());
        updateWrapper.set(Contest::getEndTime, contestVo.getEndTime());
        updateWrapper.set(Contest::getRunTime, contestVo.getRunTime());
        contestMapper.update(null, updateWrapper);
        return contestVo;
    }


    /**
     * 从数据库删除比赛
     *
     * @param id 比赛id
     */
    @Override
    public void delete(Long id) {
        contestMapper.deleteById(id);
    }


    /**
     * 返回已结束的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛列表
     */
    @Override
    public List<Contest> getEndContestList(PageParams contestPageParams) {
        Page<Contest> page = new Page<>(contestPageParams.getPage(), contestPageParams.getPageSize());
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("end_time", System.currentTimeMillis() / 1000).orderByAsc("end_time");
        Page<Contest> contestPage = contestMapper.selectPage(page, queryWrapper);
        return contestPage.getRecords();
    }


    /**
     * 返回还没开始的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛
     */
    @Override
    public List<Contest> getNotStartContestList(PageParams contestPageParams) {
        Page<Contest> page = new Page<>(contestPageParams.getPage(), contestPageParams.getPageSize());
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("start_time", System.currentTimeMillis() / 1000).orderByDesc("start_time");
        Page<Contest> contestPage = contestMapper.selectPage(page, queryWrapper);
        return contestPage.getRecords();
    }

    /**
     * 返回正在进行的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛列表
     */
    @Override
    public List<Contest> getProceedContestList(PageParams contestPageParams) {
        Page<Contest> page = new Page<>(contestPageParams.getPage(), contestPageParams.getPageSize());
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("start_time", System.currentTimeMillis() / 1000)
                .ge("end_time", System.currentTimeMillis() / 1000)
                .orderByAsc("start_time");
        Page<Contest> contestPage = contestMapper.selectPage(page, queryWrapper);
        return contestPage.getRecords();
    }

    /**
     * 添加题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 该比赛下题目列表
     */
    @Override
    public List<ProblemVo> addProblem(Long id, ProblemVo problemVo) {
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setProblemId(id);
        contestProblem.setProblemId(Long.valueOf(problemVo.getId()));
        contestProblem.setAcCount(0);
        contestProblem.setSubmitCount(0);
        contestProblemMapper.insert(contestProblem);
        return getProblemList(id);
    }

    /**
     * 从比赛删除题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @Override
    public List<ProblemVo> removeProblem(Long id, ProblemVo problemVo) {
        LambdaQueryWrapper<ContestProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContestProblem::getContestsId, id);
        queryWrapper.eq(ContestProblem::getProblemId, problemVo.getId());
        contestProblemMapper.delete(queryWrapper);
        return getProblemList(id);
    }

    /**
     * 获取相应比赛的题目列表
     *
     * @param id 比赛id
     * @return 题目列表
     */
    @Override
    public List<ProblemVo> getProblemList(Long id) {
        LambdaQueryWrapper<ContestProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContestProblem::getContestsId, id);
        List<ContestProblem> contestProblems = contestProblemMapper.selectList(queryWrapper);
        ArrayList<ProblemVo> problemVos = new ArrayList<>();
        for (ContestProblem contestProblem : contestProblems) {
            problemVos.add(problemService.getProblemById(contestProblem.getProblemId()));
        }
        return problemVos;
    }


    private Contest voToContest(ContestVo contestVo) throws ParseException {
        Contest contest = new Contest();
        if (contestVo.getId() != null) {
            contest.setId(Long.parseLong(contestVo.getId()));
        }
        contest.setTitle(contestVo.getTitle());
        contest.setStartTime(dateFormat.parse(contestVo.getStartTime()).getTime());
        contest.setEndTime(dateFormat.parse(contestVo.getEndTime()).getTime());
        contest.setRunTime(Long.parseLong(contestVo.getRunTime()));
        contest.setCreateTime(System.currentTimeMillis());
        contest.setApplicationNumber(0);
        contest.setRealNumber(0);
        if (contestVo.getPassword() != null) {
            contest.setPassword(DigestUtils.md5Hex(contestVo.getPassword() + SALT));
        }
        return contest;
    }

}
