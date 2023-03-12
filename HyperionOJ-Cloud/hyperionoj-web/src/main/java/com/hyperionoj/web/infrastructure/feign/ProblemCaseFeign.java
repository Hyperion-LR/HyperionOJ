package com.hyperionoj.web.infrastructure.feign;

import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hyperion
 */
@FeignClient(value = "hyperionoj-judge")
public interface ProblemCaseFeign {

    /**
     * 推送测试点数据到judge判题机
     * @param problemId 题目ID
     * @param inMultipartFiles 输入数据
     * @param outMultipartFiles 输出数据
     * @return 更新结果
     */
    @PostMapping(value = "/problem/case/{problemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result pushProblemCase(@PathVariable("problemId") Long problemId,
                           @RequestPart("caseInList") MultipartFile[] inMultipartFiles,
                           @RequestPart("caseOutList") MultipartFile[] outMultipartFiles);

}
