package com.hyperionoj.web.application.api;

import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.presentation.dto.JobActionDTO;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
public interface FlinkTaskService {

    /**
     * 启动作业
     *
     * @param jobActionDTO 前端入参
     * @return 启动结果
     */
    JobActionCodeEnum startJob(JobActionDTO jobActionDTO);

    /**
     * 停止作业
     *
     * @param jobActionDTO 前端入参
     * @return 停止结果
     */
    JobActionCodeEnum stopJob(JobActionDTO jobActionDTO);

}
