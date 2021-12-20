package com.hyperionoj.page.contest.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.common.vo.params.PageParams;
import com.hyperionoj.page.contest.service.ContestUserService;
import com.hyperionoj.page.contest.vo.ContestVo;
import com.hyperionoj.page.problem.vo.SubmitVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Gui
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/contest/user")
public class ContestUserController {
    @Resource
    private ContestUserService contestUserService;

    /**
     * 向比赛添加用户
     *
     * @param contestVo 添加用户参数
     */
    @PostMapping("/add")
    public Result addProblemToContest(@RequestBody ContestVo contestVo) {
        return Result.success(contestUserService.addUserToContest(contestVo));
    }


    /**
     * 向比赛删除用户
     *
     * @param contestVo 删除用户参数
     */
    @PostMapping("/delete")
    public Result deleteUserToContest(@RequestBody ContestVo contestVo) {
        contestUserService.deleteUser(contestVo);
        return Result.success(null);
    }


    /**
     * 通过比赛ID查询用户列表
     *
     * @param contestId 比赛id
     * @param params    分页查询参数
     * @return 比赛用户关系表
     */
    @PostMapping("/selectById/{contestId}")
    public Result selectUserToContestById(@PathVariable("contestId") Long contestId, @RequestBody PageParams params) {
        return Result.success(contestUserService.findProblemsByContestId(contestId, params));
    }

    /**
     * 提交代码
     *
     * @param submitVo 提交情况
     * @param id       比赛id
     * @return 结果
     */
    @PostMapping("/submit/{id}")
    public Result submit(@PathVariable("id") Long id, @RequestBody SubmitVo submitVo) {
        return Result.success(contestUserService.submit(id, submitVo));
    }

}
