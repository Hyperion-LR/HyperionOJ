package com.hyperionoj.page.contest.service;

import com.hyperionoj.common.vo.SysUserVo;
import com.hyperionoj.page.common.vo.params.PageParams;
import com.hyperionoj.page.contest.vo.ContestVo;

import java.util.List;


/**
 * @author Gui
 * @date 2021/12/18
 */
public interface ContestUserService {

    /**
     * 向比赛删除用户
     *
     * @param contestVo 向比赛删除用户的参数
     */
    void deleteUser(ContestVo contestVo);

    /**
     * 通过比赛ID查询用户列表
     *
     * @param contestId 比赛id
     * @param params    f分页参数
     * @return 题目比赛关系列表
     */
    List<SysUserVo> findProblemsByContestId(Long contestId, PageParams params);

    /**
     * 往比赛添加用户
     *
     * @param contestVo 比赛信息
     * @return 用户信息
     */
    Object addUserToContest(ContestVo contestVo);
}
