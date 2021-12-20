package com.hyperionoj.page.contest.service;


import com.hyperionoj.page.common.vo.params.PageParams;
import com.hyperionoj.page.contest.dao.pojo.Contest;
import com.hyperionoj.page.contest.vo.ContestVo;
import com.hyperionoj.page.problem.vo.ProblemVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author Gui
 * @date 2021/12/11
 */
public interface ContestService {

    /**
     * 创建比赛
     *
     * @param contestVo 创建比赛参数
     * @return 比赛详情
     * @throws ParseException 格式错误
     */
    ContestVo createContest(ContestVo contestVo) throws ParseException;

    /**
     * 取消比赛
     *
     * @param id 删除比赛对应的ID
     */
    void delete(Long id);

    /**
     * 修改比赛
     *
     * @param contestVo 修改比赛参数
     * @return 比赛详情
     */
    ContestVo update(ContestVo contestVo);

    /**
     * 返回已经结束的比赛列表
     *
     * @param pageParams 分页参数
     * @return 比赛
     */
    List<Contest> getEndContestList(PageParams pageParams);

    /**
     * 返回还没开始的比赛列表
     *
     * @param pageParams 分页参数
     * @return 比赛列表
     */
    List<Contest> getNotStartContestList(PageParams pageParams);

    /**
     * 返回正在进行的比赛列表
     *
     * @param pageParams 分页参数
     * @return 比赛列表
     */
    List<Contest> getProceedContestList(PageParams pageParams);


    /**
     * 添加题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 该比赛下题目列表
     */
    List<ProblemVo> addProblem(Long id, ProblemVo problemVo);

    /**
     * 从比赛删除题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    List<ProblemVo> removeProblem(Long id, ProblemVo problemVo);

    /**
     * 获取相应比赛的题目列表
     *
     * @param id 比赛id
     * @return 题目列表
     */
    List<ProblemVo> getProblemList(Long id);
}
