package com.hyperionoj.page.problem.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.cache.Cache;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.CommentVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import com.hyperionoj.common.vo.page.SubmitVo;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.problem.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.Constants.REDIS_KAY_PROBLEM_CACHE;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 获取题目数量
     *
     * @return 题库题目数量
     */
    @GetMapping("/count")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 60 * 60 * 1000)
    public Result getProblemCount() {
        return Result.success(problemService.getProblemCount());
    }

    /**
     * 题目列表
     *
     * @param page 分页参数
     * @return 返回查询分页
     */
    @GetMapping("/list")
    public Result getProblemList(@RequestParam("page") String page) {
        PageParams pageParams = JSONObject.parseObject(page, PageParams.class);
        return Result.success(problemService.getProblemList(pageParams));
    }

    /**
     * 获取题目分类列表
     *
     * @return 题目所有类别
     */
    @GetMapping("/category")
    public Result getCategory() {
        return Result.success(problemService.getCategory());
    }


    /**
     * 查询 题目具体情况
     *
     * @param id 题目id
     * @return 题目具体情况
     */
    @GetMapping("/{id}")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 60 * 60 * 1000)
    public Result getProblemById(@PathVariable Long id) {
        ProblemVo problem = problemService.getProblemById(id);
        if (problem == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(problem);
    }

    /**
     * 提交代码
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody SubmitVo submitVo) {
        Object result = problemService.submit(submitVo);
        if (result == null) {
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        } else {
            return Result.success(result);
        }
    }

    /**
     * 获取提交列表
     *
     * @param pageParams 分页查询参数
     * @return 根据分页参数返回简要提交信息
     */
    @GetMapping("/submits")
    public Result getSubmitList(@RequestParam("page") String pageParams) {
        return Result.success(problemService.getSubmitList(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    /**
     * 获取提交详情
     *
     * @param id 提交id
     * @return 提交结果
     */
    @GetMapping("/submit/{id}")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 60 * 60 * 1000)
    public Result getSubmit(@PathVariable("id") Long id) {
        return Result.success(problemService.getSubmitById(id));
    }

    /**
     * 对题目进行评论
     *
     * @param commentVo 用户提交评论
     * @return 本次提交情况
     */
    @PostMapping("/comment")
    public Result comment(@RequestBody CommentVo commentVo) {
        return Result.success(problemService.comment(commentVo));
    }

    /**
     * 题目下该评论的点赞数
     *
     * @param commentVo 评论参数
     * @return 目前得赞数
     */
    @PostMapping("/support/comment")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 60 * 60 * 1000)
    public Result supportComment(@RequestBody CommentVo commentVo) {
        return Result.success(problemService.support(commentVo));
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @PostMapping("/delete/comment")
    public Result deleteComment(@RequestBody CommentVo commentVo) {
        problemService.deleteComment(commentVo);
        return Result.success(null);
    }

    /**
     * 获取评论列表
     *
     * @param pageParams 分页参数
     * @return 评论列表
     */
    @GetMapping("/comments")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 60 * 60 * 1000)
    public Result getComments(@RequestParam("page") String pageParams) {
        return Result.success(problemService.getCommentList(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    /**
     * 获取最近的题目数量
     *
     * @param id 用户id缓存用
     * @return 数量列表
     */
    @GetMapping("/everyday/{id}")
    @Cache(name = REDIS_KAY_PROBLEM_CACHE, time = 15 * 60 * 1000)
    public Result getEveryday(@PathVariable("id") String id) {
        return Result.success(problemService.getEveryday());
    }

}
