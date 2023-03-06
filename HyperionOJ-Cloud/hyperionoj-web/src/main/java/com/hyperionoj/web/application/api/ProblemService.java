package com.hyperionoj.web.application.api;

import com.hyperionoj.web.domain.dto.PageParams;
import com.hyperionoj.web.presentation.vo.CategoryVO;
import com.hyperionoj.web.presentation.vo.CommentVO;
import com.hyperionoj.web.presentation.vo.ProblemVO;
import com.hyperionoj.web.presentation.vo.SubmitVO;

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
    List<ProblemVO> getProblemList(PageParams pageParams);

    /**
     * 查询 题目具体情况
     *
     * @param id 题目id
     * @return 题目具体情况
     */
    ProblemVO getProblemById(Long id);

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    Object submit(SubmitVO submitVo);

    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    ProblemVO addProblem(ProblemVO problemVo);

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    void updateProblem(ProblemVO problemVo);

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    void deleteProblem(ProblemVO problemVo);

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别1
     */
    List<CategoryVO> getCategory();

    /**
     * 添加题目分类
     *
     * @param problemCategoryVO 分类信息
     * @return 分类情况
     */
    CategoryVO addCategory(CategoryVO problemCategoryVO);

    /**
     * 删除题目分类
     *
     * @param problemCategoryVO 分类参数
     */
    void deleteCategory(CategoryVO problemCategoryVO);

    /**
     * 对题目进行评论
     *
     * @param commentVo 用户提交评论
     * @return 本次提交情况
     */
    CommentVO comment(CommentVO commentVo);

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    void deleteComment(CommentVO commentVo);

    /**
     * 获取评论列表
     *
     * @param pageParams 分页参数
     * @return 评论列表
     */
    List<CommentVO> getCommentList(PageParams pageParams);

    /**
     * 获取提交列表
     *
     * @param pageParams 分页查询参数
     * @return 根据分页参数返回简要提交信息
     */
    List<SubmitVO> getSubmitList(PageParams pageParams);

    /**
     * 获取提交详情
     *
     * @param id 提交id
     * @return 提交结果
     */
    SubmitVO getSubmitById(Long id);

    /**
     * 题目下该评论的点赞数
     *
     * @param commentVo 评论参数
     * @return 目前得赞数
     */
    Integer support(CommentVO commentVo);


    /**
     * 修改问题的缓存
     * 此方法用于redis定时回写数据库
     *
     * @param problemVo 更新的问题参数
     */
    void updateProblemCache(ProblemVO problemVo);

    /**
     * 获取题目数量
     *
     * @return 题库题目数量
     */
    Long getProblemCount();

}
