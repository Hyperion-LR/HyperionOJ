package com.hyperionoj.web.presentation.controller.admin;


import com.hyperionoj.web.application.annotation.PermissionAnnotation;
import com.hyperionoj.web.application.api.AdminService;
import com.hyperionoj.web.presentation.dto.UserDTO;
import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/admin/user")
@RestController
public class AdminUserController {

    @Resource
    private AdminService adminService;

    /**
     * 冻结普通用户账号
     * 大管理员和管理员可用
     *
     * @param userDTO 普通用户信息
     * @return 冻结信息
     */
    @PermissionAnnotation(level = 2)
    @PostMapping("/freeze")
    public Result freezeUser(@RequestBody UserDTO userDTO) {
        return Result.success(adminService.freezeUser(Long.parseLong(userDTO.getId())));
    }

}
