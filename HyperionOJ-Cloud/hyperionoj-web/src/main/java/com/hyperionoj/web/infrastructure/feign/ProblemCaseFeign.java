package com.hyperionoj.web.infrastructure.feign;

import com.hyperionoj.web.presentation.vo.Result;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "hyperionoj-judge")
public interface ProblemCaseFeign {

    @PostMapping(value = "/problem/case/{problemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result pushProblemCase(@PathVariable("problemId") Long problemId,
                           @RequestPart("caseInList") MultipartFile[] inMultipartFiles,
                           @RequestPart("caseOutList") MultipartFile[] outMultipartFiles);

}
