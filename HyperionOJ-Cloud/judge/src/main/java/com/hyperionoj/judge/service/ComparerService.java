package com.hyperionoj.judge.service;

import com.hyperionoj.judge.vo.RunResult;

/**
 * 比较代码运行结果
 *
 * @author Hyperion
 * @date 2021/12/7
 */
public interface ComparerService {

    /**
     * 比较运行结果是否正确
     *
     * @param codeRes   代码运行的结果
     * @param problemId 题目id
     * @return 代码运行结果
     */
    RunResult compare(String codeRes, String problemId);
}
