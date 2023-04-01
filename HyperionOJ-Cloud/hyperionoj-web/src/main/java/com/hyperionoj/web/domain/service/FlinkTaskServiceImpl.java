package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.FlinkTaskService;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.repo.JobWorkingRepo;
import com.hyperionoj.web.domain.submit.command.FlinkJobSubmitCommand;
import com.hyperionoj.web.domain.submit.component.JobEventComponent;
import com.hyperionoj.web.infrastructure.config.JobResourceDirConfig;
import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.infrastructure.constants.JobEventEnum;
import com.hyperionoj.web.infrastructure.constants.JobStatusEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class FlinkTaskServiceImpl implements FlinkTaskService {

    private static final Logger log = LoggerFactory.getLogger(FlinkTaskServiceImpl.class);

    @Resource
    private JobBaseRepo jobBaseRepo;

    @Resource
    private JobWorkingRepo jobWorkingRepo;

    @Resource
    private FlinkJobSubmitCommand flinkJobSubmit;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private JobResourceDirConfig jobResourceDirConfig;

    @Resource
    private JobEventComponent jobEventComponent;

    /**
     * 启动作业
     *
     * @param jobActionDTO 前端入参
     * @return 启动结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobActionCodeEnum startJob(JobActionDTO jobActionDTO) {
        UserPO userPO = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        Long jobId = jobActionDTO.getJobId();
        String startType = jobActionDTO.getStartType();

        JobBasePO job = jobBaseRepo.getById(jobId);
        JobWorkingPO jobWorkingPO = jobWorkingRepo.getOne(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, job.getId()));
        String historyStatus = job.getStatus();

        //作业已经启动中
        if(JobStatusEnum.START.getStatus().equals(historyStatus)){
            return JobActionCodeEnum.JOB_STARTING;
        }

        //作业正在运行中
        if(JobStatusEnum.RUNNING.getStatus().equals(historyStatus)){
            return JobActionCodeEnum.JOB_IS_RUNNING;
        }

        // 校验作业基础运行信息
        JobActionCodeEnum jobNoExist = startJobStatusCheck(job);
        if (jobNoExist != null) {
            return jobNoExist;
        }

        //检查系统资源是否足够job运行
        JobActionCodeEnum resourceNotEnough = jobResourceEnoughCheck(userPO.getId(), job.getCpuUsage(), job.getMemUsage(), job);
        if (resourceNotEnough != null) {
            return resourceNotEnough;
        }

        // 启动作业
        String workDir = jobResourceDirConfig.getResourceDir() + File.separator + userPO.getId() + File.separator + jobId;
        threadPoolTaskExecutor.execute(() -> flinkJobSubmit.startFlinkJob(workDir, job, jobWorkingPO));

        return JobActionCodeEnum.START_PROCESS_SUCCESS;
    }

    /**
     * 检测作业基础运行信息
     *
     * @param job 作业基本信息
     * @return 是否异常
     */
    public static JobActionCodeEnum startJobStatusCheck(JobBasePO job) {

        // 作业不存在
        if (Objects.isNull(job)) {
            return JobActionCodeEnum.JOB_NO_EXIST;
        }

        // 作业正在运行中
        if (JobStatusEnum.RUNNING.getStatus().equals(job.getStatus()) ||
                JobStatusEnum.START.getStatus().equals(job.getStatus())) {
            return JobActionCodeEnum.JOB_IS_RUNNING;
        }
        return null;
    }

    /**
     * 检查系统资源是否足够job运行
     *
     * @param projectCpuUse
     * @param projectMemUse
     * @param job
     * @return
     */
    public static JobActionCodeEnum jobResourceEnoughCheck(Long userId,Integer projectCpuUse, Integer projectMemUse, JobBasePO job) {
        if (Objects.isNull(projectCpuUse)) {
            projectCpuUse = 0;
            projectMemUse = 0;
        }
        // 判断个人配额是否充足，应为作业创建修改会检查个人配额，所以一般这里是够的
        // 判断当前flink系统是否能够支持当前作业启动

        /*
            Long tmNum = (long) Math.ceil(jobRuntimePO.getParallelism() * 1.0 / jobRuntimePO.getTmSlots());
            long curJobCpuUse = jobRuntimePO.getJmCpu() + jobRuntimePO.getTmCpu() * tmNum;
            long curJobMemoryUse = jobRuntimePO.getJmMem() + jobRuntimePO.getTmMem() * tmNum;

            Long cpuTotal = (long) (projectConfPO.getCpuLimit() * 1000);
            Long memoryTotal = (long) (projectConfPO.getMemoryLimit() * 1024);

            if (cpuTotal - projectCpuUse < curJobCpuUse || memoryTotal - projectMemUse < curJobMemoryUse) {
                return JobActionCodeEnum.RESOURCE_NOT_ENOUGH;
            }
         */

        return null;
    }

    /**
     * 停止作业
     *
     * @param jobActionDTO 前端入参
     * @return 停止结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobActionCodeEnum stopJob(JobActionDTO jobActionDTO) {
        UserPO userPO = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        // 检查当前状态是否正在运行
        Long jobId = jobActionDTO.getJobId();
        JobBasePO job = jobBaseRepo.getById(jobId);
        JobWorkingPO jobWorking = jobWorkingRepo.getOne(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, job.getId()));
        if(!JobStatusEnum.RUNNING.getStatus().equals(job.getStatus())){
            return JobActionCodeEnum.NO_RUNNING;
        }

        //TODO 判断是否savepoint当前版本没有实现


        // 开始停止作业并等待作业成功停止

        String workDir = jobResourceDirConfig.getResourceDir() + File.separator + userPO.getId() + File.separator + jobId;
        JobActionCodeEnum jobActionCodeEnum = flinkJobSubmit.stopFlinkJob(job, jobWorking, workDir);
        if(!JobActionCodeEnum.STOP_PROCESS_SUCCESS.equals(jobActionCodeEnum)){
            jobEventComponent.sendJobBaseEvent(job, JobEventEnum.STOP_FAILED);
        }else{
            jobEventComponent.sendJobBaseEvent(job, JobEventEnum.STOP_JOB_SUCCESS);
        }
        return jobActionCodeEnum;
    }
}
