package com.hyperionoj.oss.controller;

import com.hyperionoj.common.pojo.vo.ErrorCode;
import com.hyperionoj.common.pojo.vo.Result;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.vo.LoginParam;
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
@RequestMapping("/login")
@RestController
public class LoginController {

    @Resource
    private OSSService ossService;

    @PostMapping("/user")
    public Result loginById(@RequestBody LoginParam loginParam) {
        String token = ossService.login(loginParam);
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(token);
    }
}
