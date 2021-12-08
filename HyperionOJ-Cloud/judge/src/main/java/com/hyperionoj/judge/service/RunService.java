package com.hyperionoj.judge.service;

import com.hyperionoj.judge.vo.CMDResult;

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
     * @param codeLang         代码语言
     * @param compiledFileName 经过编译的代码文件
     * @param problemId        问题编号
     * @return 代码运行结果
     */
    CMDResult run(String codeLang, String compiledFileName, String problemId);
}
