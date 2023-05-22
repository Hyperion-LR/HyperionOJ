package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.AlarmService;
import com.hyperionoj.web.infrastructure.constants.JobAlarmEnum;
import org.springframework.stereotype.Service;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    /**
     * 向用户推送告警信息比如作业启动失败，运行出错，运行结束等
     *
     * @param jobId        作业id
     * @param jobAlarmEnum 告警类型
     * @param message      告警信息
     */
    @Override
    public void alarm(Long jobId, JobAlarmEnum jobAlarmEnum, String message) {

    }
}
