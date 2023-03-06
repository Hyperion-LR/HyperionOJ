package com.hyperionoj.web.presentation.controller.admin;

import com.hyperionoj.web.application.api.ProblemService;
import com.hyperionoj.web.presentation.vo.CategoryVO;
import com.hyperionoj.web.presentation.vo.CommentVO;
import com.hyperionoj.web.presentation.vo.ProblemVO;
import com.hyperionoj.web.presentation.vo.Result;
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
     * @param problemCategoryVO 分类信息
     * @return 分类情况
     */
    @RequestMapping(value = "/add/problem/category", method = RequestMethod.POST)
    public Result addProblemCategory(@RequestBody CategoryVO problemCategoryVO) {
        return Result.success(problemService.addCategory(problemCategoryVO));
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVO 分类参数
     */
    @RequestMapping(value = "/delete/problem/category", method = RequestMethod.DELETE)
    public Result deleteProblemCategory(@RequestBody CategoryVO problemCategoryVO) {
        problemService.deleteCategory(problemCategoryVO);
        return Result.success(null);
    }

    /**
     * 添加题目
     *
     * @param problemVo 题目对象
     * @return 新加的题目
     */
    @RequestMapping(value = "/add/problem", method = RequestMethod.POST)
    public Result addProblem(@RequestBody ProblemVO problemVo) {
        return Result.success(problemService.addProblem(problemVo));
    }

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    @RequestMapping(value = "/update/problem", method = RequestMethod.POST)
    public Result updateProblem(@RequestBody ProblemVO problemVo) {
        problemService.updateProblem(problemVo);
        return Result.success(null);
    }

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    @RequestMapping(value = "/delete/problem", method = RequestMethod.DELETE)
    public Result deleteProblem(@RequestBody ProblemVO problemVo) {
        problemService.deleteProblem(problemVo);
        return Result.success(null);
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @RequestMapping(value = "/delete/comment", method = RequestMethod.POST)
    public Result deleteComment(@RequestBody CommentVO commentVo) {
        problemService.deleteComment(commentVo);
        return Result.success(null);
    }

}
