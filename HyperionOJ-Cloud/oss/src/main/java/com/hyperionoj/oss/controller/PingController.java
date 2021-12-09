package com.hyperionoj.oss.controller;

import com.hyperionoj.common.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@RestController
@RequestMapping("/ping")
public class PingController {

    @GetMapping
    public Result ping() {
        return Result.success(new Date());
    }

}
