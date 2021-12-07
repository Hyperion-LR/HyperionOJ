package com.hyperionoj.judge.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
public class FilePath {

    /**
     * 代码编译文件存放地址
     */
    @Value(value = "${judge.fileDir.compiler}")
    String compileFile;

    /**
     * 提交代码存放地址
     */
    @Value(value = "${judge.fileDir.code}")
    String codeFile;

}
