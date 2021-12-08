package com.hyperionoj.judge.service;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
public interface FileService {

    /**
     * 将保存至指定目录
     *
     * @param codeFileName 代码名称
     * @param codeBody     代码
     * @return 指定保存目录
     */
    String saveFile(String codeFileName, String codeBody);
}
