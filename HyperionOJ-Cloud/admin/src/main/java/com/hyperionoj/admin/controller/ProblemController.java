package com.hyperionoj.admin.controller;

import com.hyperionoj.admin.aop.PermissionAnnotation;
import com.hyperionoj.common.feign.AdminPageClients;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.CategoryVo;
import com.hyperionoj.common.vo.page.CommentVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/admin/problem")
public class ProblemController {

    @Resource
    private AdminPageClients adminPageClients;


    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/add/problem/category", method = RequestMethod.POST)
    public Result addProblemCategory(@RequestBody CategoryVo problemCategoryVo) {
        return adminPageClients.addProblemCategory(problemCategoryVo);
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/delete/problem/category", method = RequestMethod.DELETE)
    public Result deleteProblemCategory(@RequestBody CategoryVo problemCategoryVo) {
        return adminPageClients.deleteProblemCategory(problemCategoryVo);
    }

    /**
     * 添加题目
     *
     * @param problemVo 题目信息
     * @return 新加的题目
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/add/problem", method = RequestMethod.POST)
    public Result addProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.addProblem(problemVo);
    }

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/update/problem", method = RequestMethod.POST)
    public Result updateProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.updateProblem(problemVo);
    }

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/delete/problem", method = RequestMethod.DELETE)
    public Result deleteProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.deleteProblem(problemVo);
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @PermissionAnnotation(level = 2)
    @RequestMapping(value = "/delete/comment", method = RequestMethod.DELETE)
    public Result deleteComment(@RequestBody CommentVo commentVo) {
        return adminPageClients.deleteComment(commentVo);
    }


}
