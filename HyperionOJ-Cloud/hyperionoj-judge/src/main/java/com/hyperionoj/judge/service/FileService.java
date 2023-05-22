package com.hyperionoj.judge.service;

import java.io.File;
import java.io.IOException;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
public interface FileService {

    /**
     * 将保存至指定目录
     *
     * @param codeDirName 代码名称
     * @param codeBody    代码
     * @param codeLang    代码语言
     * @return 指定保存目录
     */
    String saveFile(String codeDirName, String codeBody, String codeLang);

    /**
     * 根据代码语言选择名字及后缀
     *
     * @param codeLang 代码语言
     * @return 文件名
     */
    String codeFileName(String codeLang);

    /**
     * 删除文件
     *
     * @param folder 要删除的文件
     * @throws Exception
     */
    void deleteFolder(File folder) throws IOException;
}
