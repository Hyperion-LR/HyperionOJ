package com.hyperionoj.judge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.pojo.bo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.judge.service.*;
import com.hyperionoj.judge.vo.RunResult;
import com.hyperionoj.judge.vo.ShellResult;
import com.hyperionoj.judge.vo.SubmitVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.judge.constants.Constants.*;

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

        // 准备工作
        RunResult runResult = new RunResult();

        // 获取提交作者
        SysUser sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), SysUser.class);

        // 生成本地目录
        String codeFileName = sysUser.getId() + UNDERLINE + submit.getProblemId();

        // 保存代码至本地
        String saveDir = fileService.saveFile(codeFileName, submit.getCodeBody(), submit.getCodeLang());

        // 编译本地代码
        ShellResult compiledFile = compileService.compile(submit.getCodeLang(), saveDir);
        if (compiledFile.isStatus()) {

            // 运行本地代码
            runResult = runService.run(submit.getCodeLang(), saveDir, submit.getProblemId());

            // 测试点
            int index = 1;

            // 如果运行成功则进入比较(这里AC仅仅表示代码能运行完成生出结果)
            if (StringUtils.compare(AC, runResult.getVerdict()) == 0) {
                // 进入比较
                if (comparerService.compare(runResult.getMsg(), submit.getProblemId(), index)) {
                    runResult.setVerdict(AC);
                } else {
                    runResult.setVerdict(WA);
                    runResult.setMsg(null);
                }
            }
        } else {
            runResult.setVerdict(CE);
            runResult.setMsg(compiledFile.getMsg());
        }
        runResult.setProblemId(Integer.valueOf(submit.getProblemId()));
        return runResult;
    }

}
