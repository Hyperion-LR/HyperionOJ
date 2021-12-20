package com.hyperionoj.page.contest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.page.contest.dao.mapper.ContestProblemMapper;
import com.hyperionoj.page.contest.dao.pojo.ContestProblem;
import com.hyperionoj.page.contest.service.ContestProblemService;
import com.hyperionoj.page.contest.vo.ContestProblemPageParams;
import com.hyperionoj.page.contest.vo.ContestProblemVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gui
 * @date 2021/12/11
 */
@Service
public class ContestProblemServiceImpl implements ContestProblemService {

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private RedisSever redisSever;


    /**
     * 向比赛添加题目 id
     *
     * @param contestProblemVo 向比赛添加题目的参数
     */
    @Override
    public void addProblem(ContestProblemVo contestProblemVo) {
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setContestsId(Long.parseLong(contestProblemVo.getContestsId()));
        contestProblem.setProblemId(Long.parseLong(contestProblemVo.getProblemId()));
        contestProblemMapper.insert(contestProblem);
    }

    /**
     * 向比赛删除题目
     *
     * @param contestProblemVo 向比赛删除题目的参数
     */
    @Override
    public boolean deleteProblem(ContestProblemVo contestProblemVo) {
        LambdaQueryWrapper<ContestProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContestProblem::getContestsId, Long.parseLong(contestProblemVo.getContestsId()));
        queryWrapper.eq(ContestProblem::getProblemId, Long.parseLong(contestProblemVo.getProblemId()));
        return contestProblemMapper.delete(queryWrapper) != 0;
    }

    /**
     * 通过比赛ID查询题目列表
     *
     * @param contestProblemPageParams 分页查询参数
     */
    @Override
    public List<ContestProblem> findProblemsByContestId(ContestProblemPageParams contestProblemPageParams) {
        Page<ContestProblem> page = new Page<>(contestProblemPageParams.getPage(),
                contestProblemPageParams.getPageSize());
        QueryWrapper<ContestProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contests_id", contestProblemPageParams.getContestsId());
        Page<ContestProblem> contestProblemPage = contestProblemMapper.selectPage(page, queryWrapper);
        return contestProblemPage.getRecords();
    }
}
