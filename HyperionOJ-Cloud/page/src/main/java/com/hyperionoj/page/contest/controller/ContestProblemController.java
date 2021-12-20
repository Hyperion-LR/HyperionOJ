package com.hyperionoj.page.contest.controller;


import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.contest.dao.pojo.ContestProblem;
import com.hyperionoj.page.contest.service.ContestProblemService;
import com.hyperionoj.page.contest.vo.ContestProblemPageParams;
import com.hyperionoj.page.contest.vo.ContestProblemVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gui
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/contestProblem")
public class ContestProblemController {

    @Resource
    private ContestProblemService contestProblemService;

    /**
     * 向比赛添加题目
     *
     * @param contestProblemVo 添加题目参数
     */
    @PostMapping("/add")
    public Result addProblemToContest(@RequestBody ContestProblemVo contestProblemVo) {
        contestProblemService.addProblem(contestProblemVo);
        return Result.success(null);
    }


    /**
     * 向比赛删除题目
     *
     * @param contestProblemVo 删除题目参数
     */
    @PostMapping("/delete")
    public Result deleteProblemToContest(@RequestBody ContestProblemVo contestProblemVo) {
        if (!contestProblemService.deleteProblem(contestProblemVo)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(null);
    }

    /**
     * 通过比赛ID查询题目列表
     *
     * @param contestProblemPageParams 分页查询参数
     */
    @PostMapping("/selectById")
    public Result selectProblemToContestById(@RequestBody ContestProblemPageParams contestProblemPageParams) {
        List<ContestProblem> contestProblems = contestProblemService
                .findProblemsByContestId(contestProblemPageParams);
        return Result.success(contestProblems);
    }
}
