package com.hyperionoj.web.presentation.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.web.application.annotation.PermissionAnnotation;
import com.hyperionoj.web.application.api.AdminService;
import com.hyperionoj.web.presentation.dto.*;
import com.hyperionoj.web.presentation.dto.param.ActionPageParams;
import com.hyperionoj.web.presentation.dto.param.LoginAdminParam;
import com.hyperionoj.web.presentation.dto.param.RegisterAdminParam;
import com.hyperionoj.web.infrastructure.constants.ErrorCode;
import com.hyperionoj.web.presentation.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 管理员登录
     *
     *
     * @param loginAdminParam 新管理员登录参数
     * @return token
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginAdminParam loginAdminParam) {
        String token = adminService.login(loginAdminParam);
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(token);
    }

    /**
     * 增加管理员
     * 只有最高级的管理员才有权限
     *
     * @param registerAdminParam 新管理员注册参数
     * @return 注册成功管理员参数
     */
    @PermissionAnnotation(level = 1)
    @PostMapping("/register")
    public Result register(@RequestBody RegisterAdminParam registerAdminParam) {
        return Result.success(adminService.register(registerAdminParam));
    }

    /**
     * 更新管理员
     * 只有最高级的管理员才有权限
     *
     * @param registerAdminParam 管理员注册参数
     * @return 新管理员参数
     */
    @PermissionAnnotation(level = 1)
    @PostMapping("/update")
    public Result updateAdmin(@RequestBody RegisterAdminParam registerAdminParam) {
        return Result.success(adminService.updateAdmin(registerAdminParam));
    }

    /**
     * 删除管理员
     * 只有最高级的管理员才有权限
     *
     * @param adminDTO 管理员信息
     * @return 删除信息
     */
    @PermissionAnnotation(level = 1)
    @DeleteMapping("/delete")
    public Result deleteAdmin(@RequestBody AdminDTO adminDTO) {
        return Result.success(adminService.deleteAdmin(Long.parseLong(adminDTO.getId())));
    }

    /**
     * 查询管理员的操作
     * 只有最高级的管理员才有权限
     *
     * @param pageParams 分页查询参数
     * @return 管理员行为列表
     */
    @PermissionAnnotation(level = 3)
    @GetMapping("/query/action")
    public Result queryAdminActionList(@RequestParam("page") String pageParams) {
        return Result.success(adminService.queryActionList(JSONObject.parseObject(pageParams, ActionPageParams.class)));
    }

}
