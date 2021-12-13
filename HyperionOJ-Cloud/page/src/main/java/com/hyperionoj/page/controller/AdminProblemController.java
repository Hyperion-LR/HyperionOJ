package com.hyperionoj.page.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.dao.mapper.problem.ProblemCategoryMapper;
import com.hyperionoj.page.service.ProblemService;
import com.hyperionoj.page.vo.ProblemCategoryVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@RequestMapping("/problem/admin")
@RestController
public class AdminProblemController {

    @Resource
    private ProblemService problemService;

    @PostMapping("/add/problem/category")
    public Result addProblemCategory(@RequestBody ProblemCategoryVo problemCategoryVo){
        return Result.success(problemService.addCategory(problemCategoryVo));
    }

}
