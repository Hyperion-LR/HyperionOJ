package com.hyperionoj.page.contest.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.ContestVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.contest.service.ContestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author Gui
 * @date 2021/12/11
 */

@RestController
@RequestMapping("/contest")
public class ContestController {

    @Resource
    private ContestService contestService;

    /**
     * 创建比赛
     *
     * @param contestVo 创建比赛的参数
     * @return 比赛详情
     */
    @PostMapping("/create")
    public Result addContest(@RequestBody ContestVo contestVo) {
        try {
            return Result.success(contestService.createContest(contestVo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorCode.PARAMS_ERROR);
    }

    /**
     * 删除比赛
     *
     * @param contestVo 根据比赛的id删除
     */
    @PostMapping("/delete")
    public Result deleteContest(@RequestBody ContestVo contestVo) {
        contestService.delete(Long.parseLong(contestVo.getId()));
        return Result.success(null);
    }

    /**
     * 修改比赛
     *
     * @param contestVo 修改比赛的参数
     */
    @PostMapping("/update")
    public Result updateContest(@RequestBody ContestVo contestVo) {
        return Result.success(contestService.update(contestVo));
    }

    /**
     * 获取已经结束的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/endList")
    public Result getEndContestList(@RequestParam("page") String pageParams) {
        return Result.success(contestService.getEndContestList(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    /**
     * 获取还未开始的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/notStartList")
    public Result getNotStartContestList(@RequestParam("page") String pageParams) {
        return Result.success(contestService.getNotStartContestList(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    /**
     * 获取正在进行的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/proceedList")
    public Result getProceedContestList(@RequestParam("page") String pageParams) {
        return Result.success(contestService.getProceedContestList(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    /**
     * 往比赛添加题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/add/{id}")
    public Result addProblem(@PathVariable("id") Long id, @RequestParam("problem") String problemVo) {
        return Result.success(contestService.addProblem(id, JSONObject.parseObject(problemVo, ProblemVo.class)));
    }

    /**
     * 从比赛删除题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/remove/{id}")
    public Result removeProblem(@PathVariable("id") Long id, @RequestParam("problem") String problemVo) {
        return Result.success(contestService.removeProblem(id, JSONObject.parseObject(problemVo, ProblemVo.class)));
    }

    /**
     * 获取相应比赛的题目列表
     *
     * @param id 比赛id
     * @return 题目列表
     */
    @GetMapping("/get/{id}")
    public Result getProblemList(@PathVariable("id") Long id) {
        return Result.success(contestService.getProblemList(id));
    }

}
