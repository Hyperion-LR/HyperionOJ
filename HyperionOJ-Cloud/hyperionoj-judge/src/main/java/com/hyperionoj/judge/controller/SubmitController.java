package com.hyperionoj.judge.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.judge.service.SubmitService;
import com.hyperionoj.judge.vo.Result;
import com.hyperionoj.judge.dto.SubmitDTO;
import org.springframework.web.bind.annotation.*;

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
    public Result submit(@RequestBody SubmitDTO submitDTO) throws Exception {
        return Result.success(submitService.submit(submitDTO));
    }

}
