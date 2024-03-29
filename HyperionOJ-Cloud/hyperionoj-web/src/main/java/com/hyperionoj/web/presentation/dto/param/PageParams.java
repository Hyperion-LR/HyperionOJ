package com.hyperionoj.web.presentation.dto.param;

import lombok.Data;

/**
 * 题目分页查询参数
 *
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class PageParams {

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 题目难度
     */
    private Integer level;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 题目id
     */
    private String problemId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户id
     */
    private String authorId;

    /**
     * 提交结果
     */
    private String verdict;

    /**
     * 代码语言
     */
    private String codeLang;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 比赛开始时间
     */
    private String startTime;

    /**
     * 比赛结束时间
     */
    private String endTime;

}
