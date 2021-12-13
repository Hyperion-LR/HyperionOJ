package com.hyperionoj.page.service;

import com.hyperionoj.page.vo.*;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
public interface ProblemService {

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    List<ProblemVo> getProblemList(PageParams pageParams);

    /**
     * 查询 题目具体情况
     *
     * @param id 题目id
     * @return 题目具体情况
     */
    ProblemVo getProblemById(Long id);

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    Object submit(SubmitVo submitVo);

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别1
     */
    List<ProblemCategoryVo> getCategory();

    /**
     * 对题目进行评论
     *
     * @param commentVo 用户提交评论
     * @return 本次提交情况
     */
    boolean comment(CommentVo commentVo);

    /**
     * 获取评论列表
     *
     * @param pageParams 分页参数
     * @return 评论列表
     */
    List<CommentVo> getCommentList(PageParams pageParams);

    /**
     * 修改问题的评论数
     * 此方法用于redis定时回写数据库
     *
     * @param problemId     题目id
     * @param commentNumber 评论数量
     */
    void updateProblemCommentNumber(Long problemId, Integer commentNumber);

    /**
     * 获取提交列表
     *
     * @param pageParams 分页查询参数
     * @return 根据分页参数返回简要提交信息
     */
    List<SubmitVo> getSubmitList(PageParams pageParams);

    /**
     * 获取提交详情
     *
     * @param id 提交id
     * @return 提交结果
     */
    SubmitVo getSubmitById(Long id);

    /**
     * 添加题目分类
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    Object addCategory(ProblemCategoryVo problemCategoryVo);
}
