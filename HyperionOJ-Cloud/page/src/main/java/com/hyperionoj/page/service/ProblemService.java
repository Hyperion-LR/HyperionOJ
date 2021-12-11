package com.hyperionoj.page.service;

import com.hyperionoj.page.vo.PageParams;
import com.hyperionoj.page.vo.ProblemVo;
import com.hyperionoj.page.vo.SubmitVo;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
public interface ProblemService {

    /**
     * 返回题目列表
     *
     * @param pageParams 分页参数
     * @return 题目
     */
    List<ProblemVo> getProblemList(PageParams pageParams);

    /**
     * 查询 题目具体情况
     *
     * @param id 题目id
     * @return 题目具体情况
     */
    ProblemVo getProblemById(Long id);

    /**
     * 提交题目
     *
     * @param submitVo 用户提交数据
     * @return 本次提交情况
     */
    Object submit(SubmitVo submitVo);
}
