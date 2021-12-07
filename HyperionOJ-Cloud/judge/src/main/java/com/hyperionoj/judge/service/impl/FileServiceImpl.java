package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.service.FileService;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
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

    /**
     * 将保存至指定目录
     *
     * @param codeFileName 指定保存目录
     */
    @Override
    public void saveFile(String codeFileName, String codeBody) {
        Path codePath = Paths.get(codeFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(codePath, StandardCharsets.UTF_8)) {
            writer.write(codeBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
