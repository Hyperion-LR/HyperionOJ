package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.DataService;
import com.hyperionoj.judge.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hyperionoj.judge.constants.Constants.*;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);

    @Resource
    private FilePath filePath;

    @Resource
    private FileService fileService;

    /**
     * 更新题目测试点数据
     *
     * @param problemId      题目ID
     * @param inMultipartFiles 输入测试点文件列表
     * @param outMultipartFiles 输出测试点文件列表
     * @return 更新情况
     */
    @Override
    public Boolean updateProblemCaseData(Long problemId, MultipartFile[] inMultipartFiles, MultipartFile[] outMultipartFiles) throws IOException {
        String problemDir = filePath.getProblem() + File.separator + problemId.toString();
        String inProblemDir = problemDir + File.separator + IN;
        String outProblemDir = problemDir + File.separator + OUT;
        File file = new File(inProblemDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        for(int i = 0; i < inMultipartFiles.length; ++i){
            Path inPutDataPath = Paths.get(inProblemDir + File.separator + IN + (i + 1) + TXT);
            inMultipartFiles[i].transferTo(inPutDataPath);
        }
        file = new File(outProblemDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        for(int i = 0; i < outMultipartFiles.length; ++i){
            Path outPutDataPath = Paths.get(outProblemDir + File.separator + OUT + (i + 1) + TXT);
            outMultipartFiles[i].transferTo(outPutDataPath);
        }
        return true;
    }

    /**
     * 删除测试点数据
     * 用于更新数据跑异常时调用，防止数据污染
     *
     * @param problemId 题目id
     */
    @Override
    public void deleteCaseData(Long problemId) {
        String problemDir = filePath.getProblem() + File.separator + problemId.toString();
        try{
            fileService.deleteFolder(new File(problemDir));
        }catch (Exception e){
            log.error("删除题目测试点数据异常，请手动删除：", e);
        }
    }
}
