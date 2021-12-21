package com.hyperionoj.common.feign;

import com.hyperionoj.common.vo.ContestVo;
import com.hyperionoj.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Hyperion
 * @date 2021/12/20
 */
@FeignClient("page")
public interface ContestClients {
    /**
     * 创建比赛
     *
     * @param contestVo 创建比赛的参数
     * @return 比赛详情
     */
    @PostMapping("/create")
    Result addContest(@RequestBody ContestVo contestVo);

    /**
     * 删除比赛
     *
     * @param contestVo 根据比赛的id删除
     */
    @PostMapping("/delete")
    Result deleteContest(@RequestBody ContestVo contestVo);

    /**
     * 修改比赛
     *
     * @param contestVo 修改比赛的参数
     */
    @PostMapping("/update")
    Result updateContest(@RequestBody ContestVo contestVo);

    /**
     * 获取已经结束的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/endList")
    Result getEndContestList(@RequestBody String pageParams);

    /**
     * 获取还未开始的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/notStartList")
    Result getNotStartContestList(@RequestBody String pageParams);

    /**
     * 获取正在进行的比赛列表
     *
     * @param pageParams 比赛的分页参数
     * @return 返回分页查询
     */
    @GetMapping("/proceedList")
    Result getProceedContestList(@RequestBody String pageParams);

    /**
     * 往比赛添加题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/add/{id}")
    Result addProblem(@PathVariable("id") Long id, String problemVo);

    /**
     * 从比赛删除题目
     *
     * @param id        比赛id
     * @param problemVo 题目数据
     * @return 题目列表
     */
    @PostMapping("/remove/{id}")
    Result removeProblem(@PathVariable("id") Long id, String problemVo);

    /**
     * 获取相应比赛的题目列表
     *
     * @param id 比赛id
     * @return 题目列表
     */
    @PostMapping("/get/{id}")
    Result getProblemList(@PathVariable("id") Long id);
}
