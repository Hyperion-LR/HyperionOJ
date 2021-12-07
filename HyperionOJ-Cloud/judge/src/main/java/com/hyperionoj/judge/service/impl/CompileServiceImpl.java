package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.CompileService;
import com.hyperionoj.judge.vo.Code;
import com.hyperionoj.judge.vo.CMDResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
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
        CMDResult result = new CMDResult();
        ArrayList<String> args = getArgs(codeLang, codeFileName);
        if (args == null) {
            CMDResult errorResult = new CMDResult();
            errorResult.setStatus(false);
            errorResult.setMsg("代码语言版本错误！");
            return errorResult;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(new File(filePath.getCompileFile()));
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            int status = inputStream.read();
            if (status == 1) {
                result.setStatus(false);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"));
                String tmp;
                while ((tmp = bufferedReader.readLine()) != null) {
                    result.setMsg(result.getMsg() + tmp);
                }
            } else {
                result.setStatus(true);
                result.setMsg(filePath.getCompileFile() + codeFileName);
            }
            process.waitFor();
            inputStream.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(false);
        result.setMsg("系统错误");
        return result;
    }

    private ArrayList<String> getArgs(String codeLang, String codeFileName) {
        ArrayList<String> args = null;
        if ((Code.CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("g++");
            args.add(codeFileName);
            args.add("-o");
            args.add(codeFileName + "." + codeLang);
        } else if ((Code.JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("javac");
            args.add(codeFileName + "." + codeLang);
        } else if ((Code.PYTHON_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("python3");
            args.add(codeFileName + "." + codeLang);
        }
        return args;
    }

}
