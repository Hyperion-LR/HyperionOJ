package com.hyperionoj.admin.controller;

import com.hyperionoj.common.feign.ContestClients;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.ContestVo;
import com.hyperionoj.common.vo.page.ProblemVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/23
 */
@RestController
@RequestMapping("/admin/contest")
public class ContestAdminController {

    @Resource
    private ContestClients contestClients;

    /**
     * 创建比赛
     *
     * @param contestVo 创建比赛的参数
     * @return 比赛详情
     */
    @PostMapping("/create")
    Result addContest(@RequestBody ContestVo contestVo) {
        return contestClients.addContest(contestVo);
    }

    /**
     * 删除比赛
     *
     * @param contestVo 根据比赛的id删除
     */
    @PostMapping("/delete")
    Result deleteContest(@RequestBody ContestVo contestVo) {
        return contestClients.deleteContest(contestVo);
    }

    /**
     * 修改比赛
     *
     * @param contestVo 修改比赛的参数
     */
    @PostMapping("/update")
    Result updateContest(@RequestBody ContestVo contestVo) {
        return contestClients.updateContest(contestVo);
    }

    /**
     * 获取已经结束的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/endList")
    Result getEndContestList(@RequestBody String pageParams) {
        return contestClients.getEndContestList(pageParams);
    }

    /**
     * 获取还未开始的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/notStartList")
    Result getNotStartContestList(@RequestBody String pageParams) {
        return contestClients.getNotStartContestList(pageParams);
    }

    /**
     * 获取正在进行的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/proceedList")
    Result getProceedContestList(@RequestBody String pageParams) {
        return contestClients.getProceedContestList(pageParams);
    }

    /**
     * 往比赛添加题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/add/{id}")
    Result addProblem(@PathVariable("id") Long id, ProblemVo problemVo) {
        return contestClients.addProblem(id, problemVo);
    }

    /**
     * 从比赛删除题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/remove/{id}")
    Result removeProblem(@PathVariable("id") Long id, ProblemVo problemVo) {
        return contestClients.removeProblem(id, problemVo);
    }

    /**
     * 获取相应比赛的题目列表
     *
     * @param id 比赛id
     * @return 题目列表
     */
    @PostMapping("/get/{id}")
    Result getProblemList(@PathVariable("id") Long id) {
        return contestClients.getProblemList(id);
    }
}
