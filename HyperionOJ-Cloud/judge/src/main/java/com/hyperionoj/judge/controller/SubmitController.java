package com.hyperionoj.judge.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.judge.service.SubmitService;
import com.hyperionoj.judge.vo.Result;
import com.hyperionoj.judge.vo.SubmitVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @PostMapping
    public Result submit(@RequestParam String submit) throws Exception {
        return Result.success(submitService.submit(JSONObject.parseObject(submit, SubmitVo.class)));
    }

}
