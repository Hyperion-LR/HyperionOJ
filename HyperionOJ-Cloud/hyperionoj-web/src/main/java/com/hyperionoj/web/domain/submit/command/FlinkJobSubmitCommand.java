package com.hyperionoj.web.domain.submit.command;

import com.alibaba.fastjson.JSON;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.submit.component.JobEventComponent;
import com.hyperionoj.web.infrastructure.config.FlinkConfig;
import com.hyperionoj.web.infrastructure.config.JobResourceDirConfig;
import com.hyperionoj.web.infrastructure.constants.JobEventEnum;
import com.hyperionoj.web.infrastructure.constants.JobStatusEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import com.hyperionoj.web.infrastructure.utils.ExecuteCommandUtil;
import com.hyperionoj.web.infrastructure.utils.SubmitUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;


import static com.hyperionoj.web.infrastructure.constants.Constants.JOB_TYPE_JAR;
import static com.hyperionoj.web.infrastructure.constants.Constants.JOB_TYPE_SQL;
import static com.hyperionoj.web.infrastructure.utils.SubmitUtil.*;

/**
 * @author Hyperion
 * @date 2023/3/14
 */
@Component
public class FlinkJobSubmitCommand {

    private static final Logger log = LoggerFactory.getLogger(FlinkJobSubmitCommand.class);

    @Resource
    private JobResourceDirConfig jobResourceDirConfig;

    @Resource
    private FlinkConfig flinkConfig;

    @Resource
    private JobBaseRepo jobBaseRepo;

    @Resource
    private JobEventComponent jobEventComponent;

    /**
     * 开始启动Flink作业
     *
     * @param job 作业基本信息
     */
    public void startFlinkJob(String workDir, JobBasePO job, JobWorkingPO jobWorking) {

        StringBuilder startLog = new StringBuilder();
        String mainClass = jobWorking.getMainClass();
        String jarName = jobWorking.getJarName();
        String mainArgs = jobWorking.getMainArgs();
        Integer parallelism = jobWorking.getParallelism();
        String[] flinkArgs = getParamStrFromRuntime(flinkConfig.getPath(), parallelism, jarName, mainClass, mainArgs);
        String executeLog = "";

        ExecuteCommandUtil util = new ExecuteCommandUtil();
        try {
            if (JOB_TYPE_SQL.equals(jobWorking.getType())) {
                /*
                    String password = UUID.randomUUID().toString();
                    String sqlKey = SqlConstant.SQL_RUNTIME_PASSWORD.concat(String.valueOf(job.getId()));
                    BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(sqlKey);
                    ops.set(password, 365, TimeUnit.DAYS);
                    String configUrl = String.format("%s/api/project/%s/job/%s/version/%s/runtime", webUrl,
                            job.getProjectId(), job.getId(), jobVersion.getId());
                    String jarArgs = String.format("-config_url#%s#-password#%s", configUrl, password);
                    String dependJars = getSqlJobDepend(job.getId());
                    executeLog = submitSqlJobStart(job, checkPointPath, clusterName, flinkToolUrl, imageUrl, jarArgs,
                            workDir, flinkArgs, dependJars, clusterConf.getConfigDir(), util);
                 */
            } else if (JOB_TYPE_JAR.equals(jobWorking.getType())) {
                executeLog = submitJarJobStart(job.getName(), flinkArgs, workDir, util);
            }

            String flinkUrl = getJobUrlFromLog(executeLog);
            if (Objects.isNull(flinkUrl)) {
                throw new RuntimeException("Flink WebUrl Parse Exception~！");
            }

            // 更新作业基础信息
            job.setFlinkUrl(flinkUrl);
            jobBaseRepo.updateById(job);

            // 启动日志监听


            // 心跳监听


            // 消息推送
            jobEventComponent.sendJobBaseEvent(job, JobEventEnum.STARTING);

        } catch (Exception e) {
            log.error("[Job Start] [{}] Job Commit Error~！", job.getName(), e);
            startLog.append(util.stdOut);
            startLog.append("\n");
            startLog.append(util.stdError);
            startLog.append("\n");
            startLog.append(e);

            // 更新作业基础信息
            job.setStatus(JobStatusEnum.FAIL.getStatus());
            jobBaseRepo.updateById(job);

            // 事件推送

        }
    }



    public static String submitJarJobStart(String jobName, String[] command, String workDir, ExecuteCommandUtil util) throws Exception {

        log.info("[Job Start] Submit flink job => workDir: {}, command: {}", workDir, Arrays.toString(command));

        // 执行启动命令
        util.execCommand(command, workDir);

        log.info("[Job Start] [{}] Submit Code={}", jobName, util.exitCode);
        log.info("[Job Start] [{}] Submit stdOut={}", jobName, util.stdOut);
        log.info("[Job Start] [{}] Submit stdError={}", jobName, util.stdError);
        log.info("[Job Start] [{}] Job Start Submit Success~！", jobName);

        return util.stdOut + "\n" + util.stdError;
    }


}
