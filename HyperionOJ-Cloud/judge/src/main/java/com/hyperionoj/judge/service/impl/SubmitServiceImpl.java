package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.constants.Verdict;
import com.hyperionoj.judge.service.*;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.ShellResult;
import com.hyperionoj.judge.vo.SubmitVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

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
    public RunResult submit(SubmitVo submit) throws Exception {

        // 准备工作
        RunResult runResult = new RunResult();
        runResult.setRunMemory(0);
        runResult.setRunTime(0);

        // 生成本地目录
        String codeFileName = submit.getAuthorId() + UNDERLINE + submit.getProblemId();

        // 保存代码至本地
        String saveDir = fileService.saveFile(codeFileName, submit.getCodeBody(), submit.getCodeLang());

        // 编译本地代码
        ShellResult compiledFile = compileService.compile(submit.getCodeLang(), saveDir);
        if (compiledFile.isStatus()) {
            int caseNumber = submit.getCaseNumber();
            for (int i = 1; i <= caseNumber; ++i) {
                // 运行本地代码
                runResult = runService.run(submit.getCodeLang(), saveDir, submit.getProblemId(), submit.getRunTime(), submit.getRunMemory(), i);

                // 如果运行成功则进入比较(这里AC仅仅表示代码能运行完成生出结果)
                if (StringUtils.compare(Verdict.AC.getVerdict(), runResult.getVerdict()) == 0) {
                    // 进入比较
                    if (comparerService.compare(runResult.getMsg(), submit.getProblemId(), i)) {
                        runResult.setVerdict(Verdict.AC.getVerdict());
                    } else {
                        runResult.setVerdict(Verdict.WA.getVerdict());
                        runResult.setMsg(null);
                        break;
                    }
                }
            }
        } else {
            runResult.setVerdict(Verdict.CE.getVerdict());
            runResult.setMsg(compiledFile.getMsg());
        }
        runResult.setAuthorId(submit.getAuthorId());
        runResult.setProblemId(Long.valueOf(submit.getProblemId()));
        fileService.deleteFolder(new File(saveDir));
        return runResult;
    }

}
