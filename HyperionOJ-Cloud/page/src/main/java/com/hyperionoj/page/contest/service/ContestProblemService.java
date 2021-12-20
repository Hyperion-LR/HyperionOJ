package com.hyperionoj.page.contest.service;

import com.hyperionoj.page.contest.dao.pojo.ContestProblem;
import com.hyperionoj.page.contest.vo.ContestProblemPageParams;
import com.hyperionoj.page.contest.vo.ContestProblemVo;

import java.util.List;

/**
 * @author Gui
 * @date 2021/12/18
 */
public interface ContestProblemService {
    /**
     * 向比赛添加题目
     *
     * @param contestProblemVo 向比赛添加题目的参数
     */
    void addProblem(ContestProblemVo contestProblemVo);

    /**
     * 向比赛删除题目
     *
     * @param contestProblemVo 向比赛删除题目的参数
     */
    boolean deleteProblem(ContestProblemVo contestProblemVo);


    /**
     * 通过比赛ID查询题目列表
     *
     * @param contestProblemPageParams 分页查询参数
     * @return 题目比赛关系列表
     */
    List<ContestProblem> findProblemsByContestId(ContestProblemPageParams contestProblemPageParams);

}
