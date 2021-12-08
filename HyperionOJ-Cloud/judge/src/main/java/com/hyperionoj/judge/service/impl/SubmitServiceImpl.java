package com.hyperionoj.judge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.pojo.bo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.judge.service.*;
import com.hyperionoj.judge.vo.CMDResult;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.SubmitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.judge.constants.Constants.UNDERLINE;

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
        RunResult runResult = new RunResult();
        SysUser sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), SysUser.class);
        String codeFileName = sysUser.getId() + UNDERLINE + submit.getProblemId();
        String saveDir = fileService.saveFile(codeFileName, submit.getCodeBody(), submit.getCodeLang());
        CMDResult compiledFile = compileService.compile(submit.getCodeLang(), saveDir);
        if (compiledFile.isStatus()) {
            runResult = runService.run(submit.getCodeLang(), saveDir, submit.getProblemId());
            int index = 1;
            if (comparerService.compare(runResult.getMsg(), submit.getProblemId(), index)) {
                runResult.setVerdict("accept");
            } else {
                runResult.setVerdict("wrong answer");
                runResult.setMsg(null);
            }
        } else {
            runResult.setVerdict("wa");
            runResult.setMsg("编译失败！");
        }
        runResult.setProblemId(Integer.valueOf(submit.getProblemId()));
        return runResult;
    }

}
