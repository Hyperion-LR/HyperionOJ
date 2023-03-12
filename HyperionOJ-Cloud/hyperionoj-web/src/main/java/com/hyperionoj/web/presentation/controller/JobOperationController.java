package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.application.api.AlarmService;
import com.hyperionoj.web.application.api.FlinkTaskService;
import com.hyperionoj.web.application.api.JobOperationService;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import com.hyperionoj.web.presentation.dto.param.JobListPageParams;
import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@RestController
@RequestMapping("/job")
public class JobOperationController {

    @Resource
    private AlarmService alarmService;

    @Resource
    private JobOperationService jobOperationService;

    @Resource
    private FlinkTaskService flinkTaskService;

    /**
     * 获取用户所有的的作业列表
     * @return 作业列表
     */
    @GetMapping("/list")
    public Result jobList(@RequestBody JobListPageParams pageParams){
        return Result.success(jobOperationService.list(pageParams));
    }

    /**
     * 获取作业详情
     * @param jobId 作品ID
     * @return 作业详情情况
     */
    @GetMapping("/{jobId}")
    public Result findById(@PathVariable("jobId") Long jobId){
        return Result.success(jobOperationService.findById(jobId));
    }

    /**
     * 创建作业
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @PostMapping("/create")
    public Result createJob(@RequestBody JobBaseDTO jobBaseDTO){
        return Result.success(jobOperationService.add(jobBaseDTO));
    }

    /**
     * 更新作业
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @PostMapping("/update")
    public Result updateJob(@RequestBody JobBaseDTO jobBaseDTO){
        return Result.success(jobOperationService.update(jobBaseDTO));
    }

    /**
     * 删除作业
     * @param jobId 作业相关信息
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{jobId}")
    public Result deleteJob(@PathVariable("jobId") Long jobId){
        return Result.success(jobOperationService.delete(jobId));
    }

    /**
     * 上传资源
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    @PostMapping("/resource")
    public Result updateResource(@RequestParam("resourceList") MultipartFile[] multipartFileList){
        return Result.success(jobOperationService.updateResource(multipartFileList));
    }

    /**
     * 启动/暂停作业
     * @param jobActionDTO action参数
     * @return action结果
     */
    @PostMapping("/action")
    public Result action(@RequestBody JobActionDTO jobActionDTO){
        return Result.success(jobOperationService.jobOperate(jobActionDTO));
    }
}
