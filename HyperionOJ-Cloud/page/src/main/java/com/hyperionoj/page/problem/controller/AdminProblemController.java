package com.hyperionoj.page.problem.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.CategoryVo;
import com.hyperionoj.common.vo.page.CommentVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import com.hyperionoj.page.problem.service.ProblemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@RequestMapping("/problem/admin")
@RestController
public class AdminProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @RequestMapping(value = "/add/problem/category", method = RequestMethod.POST)
    public Result addProblemCategory(@RequestBody CategoryVo problemCategoryVo) {
        return Result.success(problemService.addCategory(problemCategoryVo));
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     */
    @RequestMapping(value = "/delete/problem/category", method = RequestMethod.DELETE)
    public Result deleteProblemCategory(@RequestBody CategoryVo problemCategoryVo) {
        problemService.deleteCategory(problemCategoryVo);
        return Result.success(null);
    }

    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    @RequestMapping(value = "/add/problem", method = RequestMethod.POST)
    public Result addProblem(@RequestBody ProblemVo problemVo) {
        return Result.success(problemService.addProblem(problemVo));
    }

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    @RequestMapping(value = "/update/problem", method = RequestMethod.POST)
    public Result updateProblem(@RequestBody ProblemVo problemVo) {
        problemService.updateProblem(problemVo);
        return Result.success(null);
    }

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    @RequestMapping(value = "/delete/problem", method = RequestMethod.DELETE)
    public Result deleteProblem(@RequestBody ProblemVo problemVo) {
        problemService.deleteProblem(problemVo);
        return Result.success(null);
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @RequestMapping(value = "/delete/comment", method = RequestMethod.POST)
    public Result deleteComment(@RequestBody CommentVo commentVo) {
        problemService.deleteComment(commentVo);
        return Result.success(null);
    }

}
