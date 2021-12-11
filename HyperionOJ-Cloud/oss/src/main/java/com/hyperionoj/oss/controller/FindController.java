package com.hyperionoj.oss.controller;

import com.hyperionoj.oss.service.SysUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@RestController
public class FindController {

    @Resource
    private SysUserService service;




}
