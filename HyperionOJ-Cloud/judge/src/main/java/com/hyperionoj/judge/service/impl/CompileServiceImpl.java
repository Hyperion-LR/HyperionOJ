package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.service.CompileService;
import com.hyperionoj.judge.service.FileService;
import com.hyperionoj.judge.vo.CMDResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.hyperionoj.judge.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Service
public class CompileServiceImpl implements CompileService {

    @Resource
    private FileService fileService;

    /**
     * 编译已经保存在本地的代码
     *
     * @param codeLang    代码语言
     * @param codeFileDir 本地目录
     * @return 代码编译后的保存目录
     */
    @Override
    public CMDResult compile(String codeLang, String codeFileDir) {
        String codeFile = codeFileDir + File.separator + fileService.codeFileName(codeLang);
        CMDResult result = new CMDResult();
        ArrayList<String> args = getArgs(codeLang, codeFile);
        if (args == null) {
            CMDResult errorResult = new CMDResult();
            errorResult.setStatus(false);
            errorResult.setMsg("代码语言版本错误！");
            return errorResult;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"));
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                result.setMsg(result.getMsg() + tmp);
            }
            if (StringUtils.isBlank(result.getMsg())) {
                result.setStatus(true);
                result.setMsg(codeFileDir + File.separator + getCompileFileName(codeLang));
            } else {
                result.setStatus(false);
            }
            process.waitFor();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(false);
        result.setMsg("系统错误");
        return result;
    }

    /**
     * 获取各语言编译后的文件
     *
     * @param codeLang 代码语言
     * @return 可执行文件名
     */
    private String getCompileFileName(String codeLang) {
        String CompileFileName = "";
        if ((CPP_LANG).equals(codeLang)) {
            CompileFileName = CPP_COMPILE;
        } else if ((JAVA_LANG).equals(codeLang)) {
            CompileFileName = JAVA_COMPILE;
        } else if ((PYTHON_LANG).equals(codeLang)) {
            CompileFileName = PYTHON_COMPILE;
        }
        return CompileFileName;
    }

    /**
     * 获取CMD参数
     *
     * @param codeLang 代码语言
     * @param codeFile 代码文件
     * @return CMD参数
     */
    private ArrayList<String> getArgs(String codeLang, String codeFile) {
        ArrayList<String> args = null;
        if ((CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(CPP_COMPILE_CMD);
            args.add("-o");
            args.add("main");
            args.add(codeFile);
        } else if ((JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(JAVA_COMPILE_CMD);
            args.add(codeFile);
        } else if ((PYTHON_LANG).equals(codeLang)) {
            // 不为null
            args = new ArrayList<>();
        }
        return args;
    }

}
