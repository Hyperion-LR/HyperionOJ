package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {

    @Value("${health.value}")
    private int testConfValue;

    @GetMapping("/health")
    public Result getProblemCount() {
        return Result.success("success!!! and Nacos config is " + (testConfValue == 1));
    }

}
