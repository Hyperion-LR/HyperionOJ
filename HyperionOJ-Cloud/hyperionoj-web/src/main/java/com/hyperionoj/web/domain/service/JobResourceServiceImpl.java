package com.hyperionoj.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.JobResourceService;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.repo.UserJobResourceRepo;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.infrastructure.config.JobResourceDirConfig;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.infrastructure.po.UserJobResourcePO;
import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Service
public class JobResourceServiceImpl implements JobResourceService {

    private static final Logger log = LoggerFactory.getLogger(JobResourceServiceImpl.class);

    @Resource
    private JobResourceDirConfig jobResourceDirConfig;

    @Resource
    private JobBaseRepo jobBaseRepo;

    @Resource
    private UserJobResourceRepo userJobResourceRepo;

    /**
     * 判断用户资源配额是否足够
     *
     * @param jobBaseDTO 作业信息
     * @return是否足够
     */
    @Override
    public Boolean jobResourceEnoughCheck(JobBaseDTO jobBaseDTO) {
        UserJobResourcePO resourceLimit = userJobResourceRepo.getOne(new LambdaQueryWrapper<UserJobResourcePO>().eq(UserJobResourcePO::getUserId, jobBaseDTO.getOwnerId()));
        List<JobBasePO> jobBasePOS = jobBaseRepo.list(new LambdaQueryWrapper<JobBasePO>().eq(JobBasePO::getOwnerId, jobBaseDTO.getOwnerId()));
        Integer cpuUsage = jobBaseDTO.getCpuUsage();
        Integer memUsage = jobBaseDTO.getMemUsage();
        for (JobBasePO job : jobBasePOS) {
            if (StringUtils.isEmpty(jobBaseDTO.getId()) || !jobBaseDTO.getId().equals(job.getId().toString())) {
                cpuUsage += job.getCpuUsage();
                memUsage += job.getMemUsage();
            }
        }
        return cpuUsage <= resourceLimit.getCpuLimit() && memUsage <= jobBaseDTO.getMemUsage();
    }

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
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 更新作业资源
     *
     * @param multipartFileList 用户ID
     * @return 是否更新成功
     */
    @Override
    public Boolean updateResource(Long jobId, MultipartFile[] multipartFileList) throws IOException {
        String resourceDir = jobResourceDirConfig.getResourceDir();
        JobBasePO job = jobBaseRepo.getById(jobId);
        File file = new File(resourceDir + File.separator + job.getOwnerId() + File.separator + jobId);
        if (file.exists()) {
            deleteFolder(file);
        }
        file.mkdirs();
        for (MultipartFile multipartFile : multipartFileList) {
            multipartFile.transferTo(Paths.get(file.getPath() + File.separator + multipartFile.getOriginalFilename()));
        }
        return true;
    }

    /**
     * 获取资源使用情况
     *
     * @param parallelism 作业并发度
     * @param jmMem       作业jm占用内存
     * @param tmMem       作业tm占用内存
     * @param tmSlot      每个tm的slot个数
     * @return 当前作业使用的了多少内存
     */
    @Override
    public Integer getMenUsage(Integer parallelism, Integer jmMem, Integer tmMem, Integer tmSlot) {
        if(parallelism == null || jmMem == null || tmMem == null || tmSlot == null){
            return 0;
        }
        int tmn = (parallelism / tmSlot) + (((parallelism % tmSlot) > 0) ? 1 : 0);
        return jmMem + tmn * tmMem;
    }

    /**
     * 获取资源使用情况
     *
     * @param parallelism 作业并发度
     * @param tmSlot      每个tm的slot个数
     * @return 上传是否成功
     */
    @Override
    public Integer getCpuUsage(Integer parallelism, Integer tmSlot) {
        if(parallelism == null || tmSlot == null){
            return 0;
        }
        return 1 + parallelism / tmSlot + (parallelism % tmSlot > 0 ? 1 : 0);
    }

    /**
     * 删除文件
     *
     * @param folder 要删除的文件
     * @throws Exception
     */
    public void deleteFolder(File folder) throws IOException {
        if (!folder.exists()) {
            log.error("文件不存在：{}", folder.getName());
            throw new IOException("文件不存在");
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
                } else {
                    //删除
                    file.delete();
                }
            }
        }
        //删除
        folder.delete();

    }


}
