package com.hyperionoj.judge.service.impl;

import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.*;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.SubmitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Service
public class SubmitServiceImpl implements SubmitService {

    @Resource
    private FilePath filePath;

    @Resource
    private FileService fileService;

    @Resource
    private CompileService compileService;

    @Resource
    private RunService runService;

    @Resource
    private ComparerService comparerService;

    /**
     * 提交代码
     *
     * @param submit 代码参数
     * @return 代码判题结果
     */
    @Override
    public RunResult submit(SubmitVo submit) {
        Object user = ThreadLocalUtils.get();
        String codeFileName = filePath.getCodeFile() + submit.getProblemId() + "_" + submit.getCodeLang();
        fileService.saveFile(codeFileName);
        String compiledFileName = compileService.compile(submit.getCodeLang(), codeFileName);
        String codeRes = runService.run(compiledFileName);
        return comparerService.compare(codeRes, submit.getProblemId());
    }

}
