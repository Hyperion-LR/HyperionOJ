package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.CompileService;
import com.hyperionoj.judge.vo.CMDResult;
import com.hyperionoj.judge.vo.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Service
public class CompileServiceImpl implements CompileService {

    @Resource
    private FilePath filePath;

    /**
     * 编译已经保存在本地的代码
     *
     * @param codeLang     代码语言
     * @param codeFileName 本地目录
     * @return 代码编译后的保存目录
     */
    @Override
    public CMDResult compile(String codeLang, String codeFileName) {
        String codeFile = filePath.getCodeFile() + codeFileName + File.separator + "Main.java";
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
                result.setMsg(filePath.getCodeFile() + codeFileName);
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

    private ArrayList<String> getArgs(String codeLang, String codeFile) {
        ArrayList<String> args = null;
        if ((Code.CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("g++");
            args.add("-o");
            args.add("main");
            args.add(codeFile);
        } else if ((Code.JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("javac");
            args.add(codeFile);
        } else if ((Code.PYTHON_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("python3");
            args.add(codeFile);
        }
        return args;
    }

}
