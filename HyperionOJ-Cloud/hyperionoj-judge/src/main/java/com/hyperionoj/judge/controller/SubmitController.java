package com.hyperionoj.judge.controller;

import com.hyperionoj.judge.dto.SubmitDTO;
import com.hyperionoj.judge.service.SubmitService;
import com.hyperionoj.judge.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Result submit(@RequestBody SubmitDTO submitDTO) throws Exception {
        return Result.success(submitService.submit(submitDTO));
    }

}
