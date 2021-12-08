package com.hyperionoj.judge.service.impl;

import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.judge.service.*;
import com.hyperionoj.judge.vo.CMDResult;
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
        String codeFileName = submit.getProblemId();
        fileService.saveFile(codeFileName, submit.getCodeBody());
        CMDResult compiledFile = compileService.compile(submit.getCodeLang(), codeFileName);
        CMDResult codeRes = runService.run(submit.getCodeLang(), compiledFile.getMsg(), submit.getProblemId());
        return comparerService.compare(codeRes.getMsg(), submit.getProblemId());
    }

}
