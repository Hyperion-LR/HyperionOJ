package com.hyperionoj.judge.service;

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
     * @param compiledFileName 经过编译的代码文件
     * @return 代码运行结果
     */
    String run(String compiledFileName);
}
