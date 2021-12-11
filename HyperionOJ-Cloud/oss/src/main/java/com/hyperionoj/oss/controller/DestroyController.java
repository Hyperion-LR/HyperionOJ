package com.hyperionoj.oss.controller;

import com.hyperionoj.common.vo.AdminVo;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.vo.LoginParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
@RequestMapping("/destroy")
@RestController
public class DestroyController {

    @Resource
    private OSSService ossService;

    @PostMapping
    public Result destroy(@RequestBody LoginParam destroyParam) {
        if (ossService.destroy(destroyParam)) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.PARAMS_ERROR);
    }

    @PostMapping("/admin")
    public Result deleteAdmin(@RequestBody AdminVo adminVo) {
        if (!ossService.deleteAdmin(Long.parseLong(adminVo.getId()))) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(null);
    }

}
