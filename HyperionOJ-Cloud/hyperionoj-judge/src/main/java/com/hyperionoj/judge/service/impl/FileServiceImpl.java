package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hyperionoj.judge.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FilePath filePath;

    /**
     * 将保存至指定目录
     *
     * @param codeDirName 代码名称
     * @param codeBody    代码
     * @param codeLang    代码语言
     * @return 指定保存目录
     */
    @Override
    public String saveFile(String codeDirName, String codeBody, String codeLang) {
        File file = new File(filePath.getSubmit() + File.separator + codeDirName);
        if (!file.exists()) {
            file.mkdir();
        }
        Path codePath = Paths.get(file.getPath() + File.separator + codeFileName(codeLang));
        try (BufferedWriter writer = Files.newBufferedWriter(codePath, StandardCharsets.UTF_8)) {
            writer.write(codeBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    /**
     * 根据代码语言选择名字及后缀
     *
     * @param codeLang 代码语言
     * @return 文件名
     */
    @Override
    public String codeFileName(String codeLang) {
        String codeFileName = "";
        if ((CPP_LANG).equals(codeLang)) {
            codeFileName = CPP_FILENAME;
        } else if ((JAVA_LANG).equals(codeLang)) {
            codeFileName = JAVA_FILENAME;
        } else if ((PYTHON_LANG).equals(codeLang)) {
            codeFileName = PYTHON_FILENAME;
        }
        return codeFileName;
    }

    /**
     * 删除文件
     *
     * @param folder 要删除的文件
     * @throws Exception
     */
    @Override
    public void deleteFolder(File folder) throws Exception {
        if (!folder.exists()) {
            throw new Exception("文件不存在");
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
                } else {
                    //删除
                    file.delete();
                }
            }
        }
        //删除
        folder.delete();

    }

}
