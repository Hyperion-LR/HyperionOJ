package com.hyperionoj.web.application.api;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
public interface JobResourceService {
    /**
     * 创建作业资源目录
     * @param userId 用户ID
     * @param jobId 作业id
     */
    void createResourceDir(Long userId, Long jobId);
}
