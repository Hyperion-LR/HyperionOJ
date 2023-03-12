package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.JobOperationService;
import com.hyperionoj.web.application.api.JobResourceService;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.repo.JobWorkingRepo;
import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.infrastructure.constants.JobStatusEnum;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import com.hyperionoj.web.presentation.dto.param.JobListPageParams;
import com.hyperionoj.web.presentation.vo.JobBaseVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class JobOperationServiceImpl implements JobOperationService {

    private static final Logger log = LoggerFactory.getLogger(JobOperationServiceImpl.class);

    @Resource
    private JobResourceService jobResourceService;

    @Resource
    private JobBaseRepo jobBaseRepo;

    @Resource
    private JobWorkingRepo jobWorkingRepo;


    /**
     * 创建作业
     *
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobBaseVO add(JobBaseDTO jobBaseDTO) {
        UserPO userPO = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        jobBaseDTO.setOwnerId(userPO.getId().toString());
        jobBaseDTO.setStatus(JobStatusEnum.NEW.getStatus());
        JobBasePO jobBasePO = MapStruct.toJobBasePO(jobBaseDTO);
        jobBasePO.setCreateTime(System.currentTimeMillis());
        jobBaseRepo.save(jobBasePO);
        //新建状态
        JobWorkingPO jobWorkingPO = JobWorkingPO.builder()
                .jobId(jobBasePO.getId())
                .type(jobBaseDTO.getType())
                .build();
        jobWorkingRepo.save(jobWorkingPO);
        jobResourceService.createResourceDir(userPO.getId(), jobBasePO.getId());
        return MapStruct.toVO(jobBasePO);
    }

    /**
     * 更新作业
     *
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobBaseVO update(JobBaseDTO jobBaseDTO) {
        JobBasePO jobBasePO = MapStruct.toJobBasePO(jobBaseDTO);
        jobBaseRepo.updateById(jobBasePO);
        JobWorkingPO jobWorkingPO = MapStruct.toJobWorkingPO(jobBaseDTO);
        jobWorkingRepo.updateById(jobWorkingPO);
        return MapStruct.toVO(jobBasePO, jobWorkingPO);
    }

    /**
     * 获取用户所有的的作业列表
     *
     * @return 作业列表
     */
    @Override
    public List<JobBaseVO> list(JobListPageParams pageParams) {
        UserPO userPO = JSONObject.parseObject((String) ThreadLocalUtils.get(), UserPO.class);
        List<JobBasePO> jobBasePOS = jobBaseRepo.list(pageParams, userPO.getId());
        return jobBasePOS.stream().map(MapStruct::toVO).collect(Collectors.toList());
    }

    /**
     * 获取作业详情
     *
     * @param jobId 作品ID
     * @return 作业详情情况
     */
    @Override
    public JobBaseVO findById(Long jobId) {
        JobBasePO jobBasePO = jobBaseRepo.getById(jobId);
        JobWorkingPO jobWorkingPO = jobWorkingRepo.getOne(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, jobId));
        return MapStruct.toVO(jobBasePO, jobWorkingPO);
    }

    /**
     * 删除作业
     *
     * @param jobId 作业id
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long jobId) {
        if (Boolean.TRUE.equals(checkDelete(jobId))) {
            jobBaseRepo.removeById(jobId);
            jobWorkingRepo.remove(new LambdaQueryWrapper<JobWorkingPO>().eq(JobWorkingPO::getJobId, jobId));
            return true;
        }
        return false;
    }

    /**
     * 删除前检查作业是否为STOP状态
     *
     * @param jobId 作业id
     * @return 是否可以删除
     */
    private Boolean checkDelete(Long jobId) {
        return JobStatusEnum.NEW.getStatus().equals(jobBaseRepo.getById(jobId).getStatus()) ||
                JobStatusEnum.END.getStatus().equals(jobBaseRepo.getById(jobId).getStatus()) ||
                JobStatusEnum.STOP.getStatus().equals(jobBaseRepo.getById(jobId).getStatus());
    }

    /**
     * 上传资源
     *
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    @Override
    public Boolean updateResource(MultipartFile[] multipartFileList) {
        return null;
    }

    /**
     * @param jobActionDTO
     * @return
     */
    @Override
    public JobActionCodeEnum jobOperate(JobActionDTO jobActionDTO) {
        return null;
    }
}
