package com.hyperionoj.page.contest.service;


import com.hyperionoj.page.contest.dao.pojo.Contest;
import com.hyperionoj.page.contest.vo.ContestPageParams;
import com.hyperionoj.page.contest.vo.ContestVo;

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
     */
    ContestVo update(ContestVo contestVo);

    /**
     * 返回已经结束的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛
     */
    List<Contest> getEndContestList(ContestPageParams contestPageParams);

    /**
     * 返回还没开始的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛列表
     */
    List<Contest> getNotStartContestList(ContestPageParams contestPageParams);

    /**
     * 返回正在进行的比赛列表
     *
     * @param contestPageParams 分页参数
     * @return 比赛列表
     */
    List<Contest> getProceedContestList(ContestPageParams contestPageParams);


}
