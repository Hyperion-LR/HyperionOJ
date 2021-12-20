package com.hyperionoj.page.contest.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.contest.service.ContestService;
import com.hyperionoj.page.contest.vo.ContestPageParams;
import com.hyperionoj.page.contest.vo.ContestVo;
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
     * @param contestPageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/endList")
    public Result getEndContestList(@RequestBody ContestPageParams contestPageParams) {
        return Result.success(contestService.getEndContestList(contestPageParams));
    }

    /**
     * 获取还未开始的比赛列表
     *
     * @param contestPageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/notStartList")
    public Result getNotStartContestList(@RequestBody ContestPageParams contestPageParams) {
        return Result.success(contestService.getNotStartContestList(contestPageParams));
    }

    /**
     * 获取正在进行的比赛列表
     *
     * @param contestPageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/proceedList")
    public Result getProceedContestList(@RequestBody ContestPageParams contestPageParams) {
        return Result.success(contestService.getProceedContestList(contestPageParams));
    }
}
