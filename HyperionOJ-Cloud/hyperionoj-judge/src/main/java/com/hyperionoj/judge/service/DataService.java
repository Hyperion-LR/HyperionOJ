package com.hyperionoj.judge.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DataService {

    /**
     * 上传题目测试点
     *
     * @param inMultipartFiles 输入测试点文件
     * @param outMultipartFiles 输出测试点文件
     */
    public Boolean updateProblemCaseData(Long problemId, MultipartFile[] inMultipartFiles, MultipartFile[] outMultipartFiles) throws IOException;

    /**
     * 删除测试点数据
     * 用于更新数据跑异常时调用，防止数据污染
     * @param problemId
     */
    void deleteCaseData(Long problemId);
}
