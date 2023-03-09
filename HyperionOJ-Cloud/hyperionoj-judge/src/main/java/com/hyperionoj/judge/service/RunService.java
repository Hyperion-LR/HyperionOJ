package com.hyperionoj.judge.service;

import com.hyperionoj.judge.vo.RunResult;

/**
 * 运行编译后的代码
 *
 * @author Hyperion
 * @date 2021/12/7
 */
public interface RunService {

    /**
     * 运行代码
     *
     * @param codeLang     代码语言
     * @param compiledFile 经过编译的代码文件
     * @param problemId    问题编号
     * @param runTime      运行时间限制
     * @param runMemory    运行内存限制
     * @param index        第几个测试点
     * @return 代码运行结果
     */
    RunResult run(String codeLang, String compiledFile, String problemId, Integer runTime, Integer runMemory, Integer index);
}
