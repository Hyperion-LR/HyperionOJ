package com.hyperionoj.page.contest.vo;

import lombok.Data;

/**
 * 题目分页查询参数
 *
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class ContestPageParams {

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 比赛开始时间
     */
    private String startTime;

    /**
     * 比赛结束时间
     */
    private String endTime;

}
