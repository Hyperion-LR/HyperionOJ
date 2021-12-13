package com.hyperionoj.admin.controller;

import com.hyperionoj.admin.aop.PermissionAnnotation;
import com.hyperionoj.admin.service.AdminService;
import com.hyperionoj.admin.vo.ActionPageParams;
import com.hyperionoj.common.feign.AdminClients;
import com.hyperionoj.common.feign.AdminPageClients;
import com.hyperionoj.common.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Resource
    private AdminClients adminClients;

    @Resource
    private AdminPageClients adminPageClients;

    @Resource
    private AdminService adminService;

    /**
     * 增加管理员
     * 只有最高级的管理员才有权限
     *
     * @param registerParam 新管理员注册参数
     * @return 注册成功管理员参数
     */
    @PermissionAnnotation(level = 1)
    @PostMapping("/add")
    public Result addAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.addAdmin(registerParam);
    }

    /**
     * 更新管理员
     * 只有最高级的管理员才有权限
     *
     * @param registerParam 管理员注册参数
     * @return 新管理员参数
     */
    @PermissionAnnotation(level = 1)
    @PostMapping("/update")
    public Result updateAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.updateAdmin(registerParam);
    }

    /**
     * 删除管理员
     * 只有最高级的管理员才有权限
     *
     * @param adminVo 管理员信息
     * @return 删除信息
     */
    @PermissionAnnotation(level = 1)
    @PostMapping("/delete")
    public Result deleteAdmin(@RequestBody AdminVo adminVo) {
        return adminClients.deleteAdmin(adminVo);
    }

    /**
     * 冻结普通用户账号
     * 大管理员和管理员可用
     *
     * @param sysUserVo 普通用户信息
     * @return 冻结信息
     */
    @PermissionAnnotation(level = 2)
    @PostMapping("/freeze")
    public Result freezeUser(@RequestBody SysUserVo sysUserVo) {
        return adminClients.freezeUser(sysUserVo);
    }

    /**
     * 查询管理员的操作
     * 只有最高级的管理员才有权限
     *
     * @param pageParams 分页查询参数
     * @return 管理员行为列表
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/query/action")
    public Result queryAdminActionList(@RequestBody ActionPageParams pageParams) {
        return Result.success(adminService.queryActionList(pageParams));
    }

    /**
     * 添加题目分类
     *
     * @param problemCategoryVo 分类信息
     * @return 分类情况
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/add/problem/category")
    public Result addProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo) {
        return adminPageClients.addProblemCategory(problemCategoryVo);
    }

    /**
     * 删除题目分类
     *
     * @param problemCategoryVo 分类参数
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/delete/problem/category")
    public Result deleteProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo) {
        return adminPageClients.addProblemCategory(problemCategoryVo);
    }

    /**
     * 添加题目
     *
     * @param problemVo 题目信息
     * @return 新加的题目
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/add/problem")
    public Result addProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.addProblem(problemVo);
    }

    /**
     * 修改题目
     *
     * @param problemVo 题目信息
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/update/problem")
    public Result updateProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.updateProblem(problemVo);
    }

    /**
     * 删除题目
     *
     * @param problemVo 题目信息
     */
    @PermissionAnnotation(level = 3)
    @PostMapping("/delete/problem")
    public Result deleteProblem(@RequestBody ProblemVo problemVo) {
        return adminPageClients.deleteProblem(problemVo);
    }

    /**
     * 删除评论
     *
     * @param commentVo 评论参数
     */
    @PermissionAnnotation(level = 2)
    @PostMapping("/delete/comment")
    public Result deleteComment(@RequestBody CommentVo commentVo) {
        return adminPageClients.deleteComment(commentVo);
    }

}
