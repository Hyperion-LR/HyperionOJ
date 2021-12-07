package com.hyperionoj.judge.service;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
public interface FileService {

    /**
     * 将保存至指定目录
     *
     * @param codeFileName 指定保存目录
     * @param codeBody     代码
     */
    void saveFile(String codeFileName, String codeBody);
}
