package com.hyperionoj.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.admin.aop.PermissionAnnotation;
import com.hyperionoj.admin.service.AdminService;
import com.hyperionoj.admin.vo.ActionPageParams;
import com.hyperionoj.common.feign.AdminClients;
import com.hyperionoj.common.vo.AdminVo;
import com.hyperionoj.common.vo.RegisterParam;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.SysUserVo;
import org.springframework.web.bind.annotation.*;

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
    private AdminService adminService;

    /**
     * 增加管理员
     * 只有最高级的管理员才有权限
     *
     * @param registerParam 新管理员注册参数
     * @return 注册成功管理员参数
     */
    @PermissionAnnotation(level = 1)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
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
    @RequestMapping(value = "/update", method = RequestMethod.POST)
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
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
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
    @RequestMapping(value = "/query/action", method = RequestMethod.GET)
    public Result queryAdminActionList(@RequestParam("page") String pageParams) {
        return Result.success(adminService.queryActionList(JSONObject.parseObject(pageParams, ActionPageParams.class)));
    }

}
