package com.hyperionoj.page.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.service.ProblemService;
import com.hyperionoj.page.vo.PageParams;
import com.hyperionoj.page.vo.ProblemVo;
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
     * 查询 题目简要情况
     *
     * @param id 题目id
     * @return 题目简要情况
     */
    @GetMapping("/{id}")
    public Result getProblemById(@PathVariable Long id) {
        ProblemVo problem = problemService.getProblemById(id);
        if (problem == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(problem);
    }
}
