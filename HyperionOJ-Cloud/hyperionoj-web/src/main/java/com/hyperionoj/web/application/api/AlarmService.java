package com.hyperionoj.web.application.api;

import com.hyperionoj.web.infrastructure.constants.JobAlarmEnum;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
public interface AlarmService {

    /**
     * 向用户推送告警信息比如作业启动失败，运行出错，运行结束等
     * @param jobId 作业id
     * @param jobAlarmEnum 告警类型
     * @param message 告警信息
     */
    void alarm(Long jobId, JobAlarmEnum jobAlarmEnum, String message);

}
