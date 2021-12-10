package com.hyperionoj.admin.controller;

import com.hyperionoj.admin.aop.AdminActionAnnotation;
import com.hyperionoj.admin.service.AdminService;
import com.hyperionoj.admin.vo.ActionPageParams;
import com.hyperionoj.common.feign.AdminClients;
import com.hyperionoj.common.vo.RegisterParam;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.SysUserVo;
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
    private AdminService adminService;

    @AdminActionAnnotation(url = "/add")
    @PostMapping("/add")
    public Result addAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.addAdmin(registerParam);
    }

    @AdminActionAnnotation(url = "/update")
    @PostMapping("/update")
    public Result updateAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.updateAdmin(registerParam);
    }

    @AdminActionAnnotation(url = "/delete")
    @PostMapping("/delete")
    public Result deleteAdmin(@RequestBody SysUserVo sysUserVo) {
        return adminClients.deleteAdmin(sysUserVo);
    }

    @AdminActionAnnotation(url = "/freeze")
    @PostMapping("/freeze")
    public Result freezeUser(@RequestBody SysUserVo sysUserVo) {
        return adminClients.freezeUser(sysUserVo);
    }

    @PostMapping("/query/action")
    public Result QueryAdminActionList(@RequestBody ActionPageParams pageParams) {
        return Result.success(adminService.queryActionList(pageParams));
    }

}
