package com.hyperionoj.judge.service;

import com.hyperionoj.judge.vo.ShellResult;

/**
 * 提供编译服务
 *
 * @author Hyperion
 * @date 2021/12/7
 */
public interface CompileService {

    /**
     * 编译已经保存在本地的代码
     *
     * @param codeLang     代码语言
     * @param codeFileName 本地目录
     * @return 代码编译后的保存目录
     */
    ShellResult compile(String codeLang, String codeFileName);
}
