package com.hyperionoj.web.application.api;

import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.infrastructure.exception.JobResourceNotEnoughException;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import com.hyperionoj.web.presentation.dto.param.JobListPageParams;
import com.hyperionoj.web.presentation.vo.JobBaseVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
public interface JobOperationService {

    /**
     * 创建作业
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    JobBaseVO add(JobBaseDTO jobBaseDTO) throws JobResourceNotEnoughException;

    /**
     * 更新作业
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    JobBaseVO update(JobBaseDTO jobBaseDTO) throws JobResourceNotEnoughException;


    /**
     * 获取用户所有的的作业列表
     * @param pageParams 分页参数
     * @return 作业列表
     */
    List<JobBaseVO> list(JobListPageParams pageParams);

    /**
     * 获取作业详情
     * @param jobId 作品ID
     * @return 作业详情情况
     */
    JobBaseVO findById(Long jobId);

    /**
     * 删除作业
     * @param jobId 作业id
     * @return 是否删除成功
     */
    Boolean delete(Long jobId);

    /**
     * 上传资源
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    Boolean updateResource(Long jobId, MultipartFile[] multipartFileList);

}
