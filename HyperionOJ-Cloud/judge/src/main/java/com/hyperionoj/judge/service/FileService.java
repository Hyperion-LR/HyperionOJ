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
     */
    void saveFile(String codeFileName);
}
