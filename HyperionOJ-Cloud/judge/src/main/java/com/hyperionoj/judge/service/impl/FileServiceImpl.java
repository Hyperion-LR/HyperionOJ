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
     * @param codeFileName 代码名称
     * @param codeBody     代码
     * @return 指定保存目录
     */
    @Override
    public String saveFile(String codeFileName, String codeBody) {
        File file = new File(filePath.getCodeFile() + codeFileName);
        if (!file.exists()) {
            file.mkdir();
        }
        codeFileName = file.getPath() + File.separator + "Main.java";
        Path codePath = Paths.get(codeFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(codePath, StandardCharsets.UTF_8)) {
            writer.write(codeBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codeFileName;
    }
}
