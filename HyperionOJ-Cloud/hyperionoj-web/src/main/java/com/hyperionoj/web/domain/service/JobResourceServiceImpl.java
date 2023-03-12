package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.JobResourceService;
import com.hyperionoj.web.infrastructure.config.JobResourceDirConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class JobResourceServiceImpl implements JobResourceService {

    @Resource
    private JobResourceDirConfig jobResourceDirConfig;

    /**
     * 创建作业资源目录
     *
     * @param userId 用户ID
     * @param jobId  作业id
     */
    @Override
    public void createResourceDir(Long userId, Long jobId) {
        String resourceDir = jobResourceDirConfig.getResourceDir();
        File file = new File(resourceDir + File.separator + userId + File.separator + jobId);
        if(!file.exists()){
           file.mkdirs();
        }
    }
}
