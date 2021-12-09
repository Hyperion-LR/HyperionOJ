package com.hyperionoj.page.service;

import com.hyperionoj.page.vo.PageParams;
import com.hyperionoj.page.vo.ProblemVo;

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
     * 通过题目id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    ProblemVo getProblemById(Long id);

}
