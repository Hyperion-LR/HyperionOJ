package com.hyperionoj.oss.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.oss.dao.pojo.admin.Admin;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.service.VerCodeService;
import com.hyperionoj.oss.vo.RegisterAdminParam;
import com.hyperionoj.oss.vo.RegisterParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@RequestMapping("/register")
@RestController
public class RegisterController {
    @Resource
    private OSSService ossService;

    @Resource
    private VerCodeService verCodeService;

    @PostMapping("/user")
    public Result userRegisterById(@RequestBody RegisterParam registerParam) {
        if (!verCodeService.checkCode(registerParam.getMail(), registerParam.getVerCode())) {
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        String token = ossService.registerUser(registerParam);
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(token);
    }

    @PostMapping("/admin")
    public Result addAdmin(@RequestBody RegisterAdminParam registerParam) {
        Admin admin = ossService.addAdmin(registerParam);
        if (admin == null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        return Result.success(admin);
    }
}
