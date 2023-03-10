package com.hyperionoj.web.infrastructure.feign;

import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "hyperionoj-judge")
public interface ProblemCaseFeign {

    @PostMapping(value = "/problem/case/{problemId}")
    Result pushProblemCase(Long problemId,
                           @RequestParam("caseInList") MultipartFile[] inMultipartFiles,
                           @RequestParam("caseOutList") MultipartFile[] outMultipartFiles);

}
