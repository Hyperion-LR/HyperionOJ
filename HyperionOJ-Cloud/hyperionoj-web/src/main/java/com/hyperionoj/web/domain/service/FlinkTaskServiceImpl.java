package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.FlinkTaskService;
import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import org.springframework.stereotype.Service;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class FlinkTaskServiceImpl implements FlinkTaskService {
    /**
     * 启动作业
     *
     * @param jobActionDTO 前端入参
     * @return 启动结果
     */
    @Override
    public JobActionCodeEnum startJob(JobActionDTO jobActionDTO) {
        return null;
    }

    /**
     * 停止作业
     *
     * @param jobActionDTO 前端入参
     * @return 停止结果
     */
    @Override
    public JobActionCodeEnum stopJob(JobActionDTO jobActionDTO) {
        return null;
    }
}
