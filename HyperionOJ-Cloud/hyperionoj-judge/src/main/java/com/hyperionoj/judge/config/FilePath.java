package com.hyperionoj.judge.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
@Configuration
public class FilePath {

    /**
     * 题目数据
     */
    @Value("${judge.fileDir.problem}")
    String problem;

    /**
     * 题目数据
     */
    @Value("${judge.fileDir.submit}")
    String submit;

}
