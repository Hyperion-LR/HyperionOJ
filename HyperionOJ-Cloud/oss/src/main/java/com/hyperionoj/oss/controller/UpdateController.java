package com.hyperionoj.oss.controller;

import com.hyperionoj.common.pojo.vo.Result;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.vo.SysUserVo;
import com.hyperionoj.oss.vo.UpdatePasswordParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@RequestMapping("/update")
@RestController
public class UpdateController {

    @Resource
    private OSSService ossService;

    @PostMapping("/user")
    public Result byUser(@RequestBody SysUserVo userVo) {
        ossService.updateUser(userVo);
        return Result.success(null);
    }

    @PostMapping("/password")
    public Result byUser(@RequestBody UpdatePasswordParam updateParam) {
        ossService.updatePassword(updateParam);
        return Result.success(null);
    }

}