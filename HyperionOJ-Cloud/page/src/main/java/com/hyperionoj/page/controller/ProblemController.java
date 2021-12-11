package com.hyperionoj.page.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.service.ProblemService;
import com.hyperionoj.page.vo.PageParams;
import com.hyperionoj.page.vo.ProblemVo;
import com.hyperionoj.page.vo.SubmitVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 题目列表
     *
     * @param pageParams 分页参数
     * @return 返回查询分页
     */
    @GetMapping("/list")
    public Result getProblemList(@RequestBody PageParams pageParams) {
        return Result.success(problemService.getProblemList(pageParams));
    }

    /**
     * 查询 题目具体情况
     *
     * @param id 题目id
     * @return 题目具体情况
     */
    @GetMapping("/{id}")
    public Result getProblemById(@PathVariable Long id) {
        ProblemVo problem = problemService.getProblemById(id);
        if (problem == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(problem);
    }

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody SubmitVo submitVo) {
        Object result = problemService.submit(submitVo);
        if (result == null) {
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        } else {
            return Result.success(result);
        }
    }


}
