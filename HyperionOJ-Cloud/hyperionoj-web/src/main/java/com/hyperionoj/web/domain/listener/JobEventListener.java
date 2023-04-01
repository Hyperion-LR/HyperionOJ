package com.hyperionoj.web.domain.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.repo.JobWorkingRepo;
import com.hyperionoj.web.domain.submit.component.JobEventComponent;
import com.hyperionoj.web.infrastructure.config.YarnConfig;
import com.hyperionoj.web.infrastructure.constants.JobEventEnum;
import com.hyperionoj.web.infrastructure.constants.JobStatusEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import com.hyperionoj.web.infrastructure.utils.ExecuteCommandUtil;
import com.hyperionoj.web.infrastructure.utils.SubmitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hyperionoj.web.infrastructure.constants.Constants.Application_STATE;


/**
 * @author Hyperion
 * @date 2023/3/15
 */
@Component
public class JobEventListener implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(JobEventListener.class);

    @Resource
    private JobBaseRepo jobBaseRepo;

    @Resource
    private JobWorkingRepo jobWorkingRepo;

    @Resource
    private JobEventComponent jobEventComponent;

    @Resource
    private YarnConfig yarnConfig;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3 * 1000);
                scanJobState();
            } catch (Exception e) {
                log.error("[K8sPodMoniter] error ", e);
            }
        }

    }

    /**
     * 扫描所有正在启动，运行的job判断是否正常运行并通知
     */
    void scanJobState(){
        // 查看START状态的是否成功启动
        List<JobBasePO> jobBasePOList = jobBaseRepo.list(new LambdaQueryWrapper<JobBasePO>().eq(JobBasePO::getStatus, JobStatusEnum.START.getStatus()));
        for(JobBasePO jobBasePO : jobBasePOList){
            JobWorkingPO jobWorkingPO = jobWorkingRepo.getOne(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, jobBasePO.getId()));
            String applicationId = jobWorkingPO.getApplicationId();
            ExecuteCommandUtil util = new ExecuteCommandUtil();
            String[] scanAppCommand = getScanAppCommand(applicationId);
            try {
                String workDir = yarnConfig.getPath();
                workDir = workDir.substring(0, workDir.length() - 4);
                util.execCommand(scanAppCommand, workDir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String stdOut = util.stdOut;

            if(stdOut == null){
                // 如果没有结果可能作业已经被删除，运行失败,发送告警并更新状态
                jobBasePO.setStatus(JobStatusEnum.END.getStatus());
                jobEventComponent.sendJobBaseEvent(jobBasePO, JobEventEnum.START_FAILED);
                jobBaseRepo.updateById(jobBasePO);
                continue;
            }
            String stdError = util.stdError;
            String jobState = SubmitUtil.parseData(stdOut, Application_STATE);
            if(JobStatusEnum.RUNNING.getStatus().equals(jobState)){
                // 成功启动
                jobBasePO.setStatus(JobStatusEnum.RUNNING.getStatus());
                jobEventComponent.sendJobBaseEvent(jobBasePO, JobEventEnum.START_JOB_SUCCESS);
                jobBasePO.setFlinkUrl("http://39.98.181.188:2280/proxy/" + applicationId);
                jobBaseRepo.updateById(jobBasePO);
            } else if (!JobStatusEnum.ACCEPTED.getStatus().equals(jobState)) {
                // 启动失败
                jobBasePO.setStatus(jobState);
                jobEventComponent.sendJobBaseEvent(jobBasePO, JobEventEnum.START_FAILED);
                jobBaseRepo.updateById(jobBasePO);
            }
        }

        // 查看RUNNING状态的job的状态是否正常
        jobBasePOList = jobBaseRepo.list(new LambdaQueryWrapper<JobBasePO>().eq(JobBasePO::getStatus, JobStatusEnum.RUNNING.getStatus()));
        for(JobBasePO jobBasePO : jobBasePOList){
            JobWorkingPO jobWorkingPO = jobWorkingRepo.getOne(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, jobBasePO.getId()));
            String applicationId = jobWorkingPO.getApplicationId();
            ExecuteCommandUtil util = new ExecuteCommandUtil();
            String[] scanAppCommand = getScanAppCommand(applicationId);
            try {
                String workDir = yarnConfig.getPath();
                workDir = workDir.substring(0, workDir.length() - 4);
                util.execCommand(scanAppCommand, workDir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String stdOut = util.stdOut;
            if(stdOut == null){
                // 如果没有结果可能作业已经被删除，运行失败,发送告警并更新状态
                jobBasePO.setStatus(JobStatusEnum.END.getStatus());
                jobEventComponent.sendJobBaseEvent(jobBasePO, JobEventEnum.RUN_FAILED);
                jobBaseRepo.updateById(jobBasePO);
                continue;
            }
            String stdError = util.stdError;
            String jobState = SubmitUtil.parseData(stdOut, Application_STATE);
            if(!JobStatusEnum.RUNNING.getStatus().equals(jobState)){
                // 如果不是Running状态说明可能已经停止需要更新信息并告警通知
                jobBasePO.setFlinkUrl("");
                jobWorkingPO.setApplicationId("");
                jobBasePO.setStatus(Objects.requireNonNull(JobStatusEnum.getJobStatusEnum(jobState)).getStatus());
                jobEventComponent.sendJobBaseEvent(jobBasePO, JobEventEnum.RUN_FAILED);
                jobBaseRepo.updateById(jobBasePO);
            }
        }
    }

    private String[] getScanAppCommand(String applicationId) {
        List<String> args = new ArrayList<>();
        args.add(yarnConfig.getPath());
        args.add("application");
        args.add("-status");
        args.add(applicationId);
        return args.toArray(String[]::new);
    }

}
