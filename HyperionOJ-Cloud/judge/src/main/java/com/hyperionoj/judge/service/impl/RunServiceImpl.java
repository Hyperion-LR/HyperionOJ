package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.RunService;
import com.hyperionoj.judge.vo.CMDResult;
import com.hyperionoj.judge.vo.Code;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
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
        // 获取题目输入文件，并做好线程运行准备
        String inDir = filePath.getInDir() + File.separator + problemId + File.separator + "in1.txt";
        CMDResult result = new CMDResult();
        ArrayList<String> args = getArgs(codeLang);
        if (args == null) {
            CMDResult errorResult = new CMDResult();
            errorResult.setStatus(false);
            errorResult.setMsg("代码语言错误！");
            return errorResult;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(new File(compiledFile));

        try {
            // 正式开始启动程序
            Process process = processBuilder.start();

            // 记录启动时间
            long start = System.currentTimeMillis();

            // 向子进程输入数据和获取运行结果
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inDir)));
                 PrintStream ps = new PrintStream(process.getOutputStream());
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String tmp;
                while ((tmp = fileReader.readLine()) != null) {
                    // 向子进程输入数据
                    ps.println(tmp);
                }

                // 关闭IO流防止堵塞
                ps.close();
                fileReader.close();

                // 开始获取运行结果
                StringBuilder res = new StringBuilder();
                while ((tmp = bufferedReader.readLine()) != null) {
                    // 如果超时直接销毁子进程
                    if (System.currentTimeMillis() - start > 110000) {
                        process.destroy();
                        // 把流中的数据消耗掉
                        while (bufferedReader.readLine() != null) {
                            bufferedReader.lines();
                        }
                    }
                    res.append(tmp).append('\n');
                }

                // 等待子进程结束
                process.waitFor(1000, TimeUnit.NANOSECONDS);

                // 判断是否超时
                long end = System.currentTimeMillis();
                if (end - start < 1100) {
                    result.setStatus(true);
                    result.setMsg(res.toString());
                } else {
                    result.setStatus(false);
                    result.setMsg("超时");
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStatus(false);
        result.setMsg("系统错误");
        return result;
    }

    private ArrayList<String> getArgs(String codeLang) {
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
