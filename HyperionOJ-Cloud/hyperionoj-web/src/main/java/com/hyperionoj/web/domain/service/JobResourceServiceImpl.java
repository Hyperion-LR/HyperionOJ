package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.JobResourceService;
import com.hyperionoj.web.domain.repo.JobBaseRepo;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.infrastructure.config.JobResourceDirConfig;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
        for(MultipartFile multipartFile : multipartFileList){
            multipartFile.transferTo(Paths.get(file.getPath() + File.separator + multipartFile.getOriginalFilename()));
        }
        return true;
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
