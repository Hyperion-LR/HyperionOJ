package com.hyperionoj.judge.controller;

import com.hyperionoj.judge.constants.ErrorCode;
import com.hyperionoj.judge.service.DataService;
import com.hyperionoj.judge.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Hyperion
 * @date 2023/3/10
 */

@RestController
@RequestMapping("/problem")
public class DataController {

    @Resource
    private DataService dataService;

    @PostMapping(value = "/case/{problemId}")
    public Result updateProblemCaseNumber(Long problemId,
                                          @RequestParam("caseInList") MultipartFile[] inMultipartFiles,
                                          @RequestParam("caseOutList") MultipartFile[] outMultipartFiles){
        try {
            return Result.success(dataService.updateProblemCaseData(problemId, inMultipartFiles, outMultipartFiles));
        }catch (IOException e){
            dataService.deleteCaseData(problemId);
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        }
    }

}
