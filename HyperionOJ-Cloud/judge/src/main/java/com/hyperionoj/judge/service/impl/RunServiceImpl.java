package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.RunService;
import com.hyperionoj.judge.vo.CMDResult;
import com.hyperionoj.judge.vo.Code;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Service
public class RunServiceImpl implements RunService {

    @Resource
    private FilePath filePath;

    /**
     * 运行代码
     *
     * @param codeLang     代码语言
     * @param compiledFile 经过编译的代码文件
     * @return 代码运行结果
     */
    @Override
    public CMDResult run(String codeLang, String compiledFile, String problemId) {
        String inDir = filePath.getInDir() + File.separator + problemId + File.separator + "in1.txt";
        String resDir = filePath.getResDir() + File.separator + problemId;
        File resFile = new File(resDir);
        if (!resFile.exists()) {
            resFile.mkdir();
        }
        String resFilename = resFile.getPath() + File.separator + "res.txt";
        Path resPath = Paths.get(resFilename);
        CMDResult result = new CMDResult();

        ArrayList<String> args = getArgs(codeLang, compiledFile);
        if (args == null) {
            CMDResult errorResult = new CMDResult();
            errorResult.setStatus(false);
            errorResult.setMsg("代码语言错误！");
            return errorResult;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(resFile);
        try {
            Process process = processBuilder.start();
            long start = System.currentTimeMillis();
            if (inDir != null) {
                try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inDir)));
                     PrintStream ps = new PrintStream(process.getOutputStream())) {
                    String tmp;
                    while ((tmp = fileReader.readLine()) != null) {
                        ps.println(tmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String tmp;
                while ((tmp = bufferedReader.readLine()) != null) {
                    if (System.currentTimeMillis() - start > 110000) {
                        result.setStatus(false);
                        result.setMsg("程序已经超时！");
                        process.destroy();
                        while (bufferedReader.readLine() != null) {
                            bufferedReader.lines();
                        }
                        break;
                    }
                    result.setMsg(result.getMsg() + tmp);
                }
                long end = System.currentTimeMillis();
                System.out.println("共用时:" + (end - start));
                process.waitFor(1000, TimeUnit.NANOSECONDS);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(false);
        result.setMsg("系统错误");
        return result;
    }

    private ArrayList<String> getArgs(String codeLang, String compiledFileName) {
        ArrayList<String> args = null;
        if ((Code.CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("Main.exe");
        } else if ((Code.JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("java");
            args.add("Main");
        } else if ((Code.PYTHON_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add("python3");
            args.add("Main.py");
        }
        return args;
    }
}
