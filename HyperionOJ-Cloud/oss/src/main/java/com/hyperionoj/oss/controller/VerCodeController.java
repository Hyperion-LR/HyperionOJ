package com.hyperionoj.oss.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.oss.service.VerCodeService;
import com.hyperionoj.oss.vo.MailCodeParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
@RequestMapping("/vercode")
@RestController
public class VerCodeController {

    @Resource
    VerCodeService verCodeService;

    @PostMapping
    public Result getCode(@RequestBody MailCodeParam mailCodeParam) {
        verCodeService.getCode(mailCodeParam.getMail(), mailCodeParam.getSubject());
        return Result.success(null);
    }

}
