package com.hyperionoj.page.contest.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.cache.Cache;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.ContestVo;
import com.hyperionoj.common.vo.page.SubmitVo;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.contest.service.ContestUserService;
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
    public Result addProblemToContest(@RequestBody String contestVo) {
        return Result.success(contestUserService.addUserToContest(JSONObject.parseObject(contestVo, ContestVo.class)));
    }


    /**
     * 向比赛删除用户
     *
     * @param contestVo 删除用户参数
     */
    @PostMapping("/delete")
    public Result deleteUserToContest(@RequestBody String contestVo) {
        contestUserService.deleteUser(JSONObject.parseObject(contestVo, ContestVo.class));
        return Result.success(null);
    }


    /**
     * 通过比赛ID查询用户列表
     *
     * @param contestId 比赛id
     * @param params    分页查询参数
     * @return 比赛用户关系表
     */
    @GetMapping("/selectById/{contestId}")
    public Result selectUserToContestById(@PathVariable("contestId") Long contestId, @RequestParam("page") String params) {
        return Result.success(contestUserService.findProblemsByContestId(contestId, JSONObject.parseObject(params, PageParams.class)));
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

    /**
     * 排行榜
     *
     * @param contestId 比赛id
     * @return 排行榜
     */
    @Cache(name = "contest", time = 24 * 60 * 60 * 1000)
    @GetMapping("/rank/{id}")
    public Result rankList(@PathVariable("id") Long contestId) {
        return Result.success(contestUserService.rank(contestId));
    }

}
