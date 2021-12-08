package com.hyperionoj.judge.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
@Component
public class FilePath {

    /**
     * 提交代码存放地址
     */
    @Value("${judge.fileDir.code}")
    String codeFile;

    /**
     * 输入数据目录
     */
    @Value("${judge.fileDir.in}")
    String inDir;

    /**
     * 输出数据目录
     */
    @Value("${judge.fileDir.out}")
    String outDir;

    /**
     * 结果目录
     */
    @Value("${judge.fileDir.res}")
    String resDir;

}
