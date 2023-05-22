package com.hyperionoj.web.application.api;

import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
public interface JobResourceService {

    /**
     * 判断用户资源配额是否足够
     *
     * @param jobBaseDTO 作业信息
     * @return是否足够
     */
    Boolean jobResourceEnoughCheck(JobBaseDTO jobBaseDTO);

    /**
     * 创建作业资源目录
     *
     * @param userId 用户ID
     * @param jobId  作业id
     */
    void createResourceDir(Long userId, Long jobId);

    /**
     * 上传资源
     *
     * @param jobId             作业id
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    Boolean updateResource(Long jobId, MultipartFile[] multipartFileList) throws IOException;

    /**
     * 获取资源使用情况
     *
     * @param parallelism 作业并发度
     * @param jmMem       作业jm占用内存
     * @param tmMem       作业tm占用内存
     * @param tmSlot      每个tm的slot个数
     * @return 上传是否成功
     */
    Integer getMenUsage(Integer parallelism, Integer jmMem, Integer tmMem, Integer tmSlot);

    /**
     * 获取资源使用情况
     *
     * @param parallelism 作业并发度
     * @param tmSlot      每个tm的slot个数
     * @return 上传是否成功
     */
    Integer getCpuUsage(Integer parallelism, Integer tmSlot);
}
