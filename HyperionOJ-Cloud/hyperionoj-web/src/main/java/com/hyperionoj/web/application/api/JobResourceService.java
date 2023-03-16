package com.hyperionoj.web.application.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 上传资源
     * @param jobId 作业id
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    Boolean updateResource(Long jobId, MultipartFile[] multipartFileList) throws IOException;

}
