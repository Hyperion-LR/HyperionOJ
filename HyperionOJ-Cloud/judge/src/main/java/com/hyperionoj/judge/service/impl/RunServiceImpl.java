package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.RunService;
import com.hyperionoj.judge.vo.RunResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.hyperionoj.judge.constants.Constants.*;

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
    public RunResult run(String codeLang, String compiledFile, String problemId) {
        // 获取题目输入文件，并做好进程运行准备
        String inData = filePath.getProblem() + File.separator + problemId + File.separator + "in" + File.separator + "in1.txt";
        RunResult result = new RunResult();
        ArrayList<String> args = getArgs(codeLang);
        if (args == null) {
            result.setVerdict(CE);
            result.setMsg("代码语言错误！");
            return result;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(new File(compiledFile));
        try {
            // 正式开始启动程序
            Process process = processBuilder.start();

            // 记录启动时间
            long start = System.currentTimeMillis();

            // 向子进程输入数据和获取运行结果
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inData)));
                 PrintStream ps = new PrintStream(process.getOutputStream());
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
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

                // 没有输出信息判断运行出错
                if (res.length() == 0) {
                    while ((tmp = errorReader.readLine()) != null) {
                        res.append(tmp).append('\n');
                    }
                    errorReader.close();
                    result.setVerdict(RE);
                    result.setMsg(res.toString());
                } else {
                    // 等待子进程结束
                    process.waitFor(1000, TimeUnit.NANOSECONDS);

                    // 判断是否超时
                    long end = System.currentTimeMillis();
                    if (end - start < 1100) {

                        // 没超时设置相关信息，这里不考虑结果对错之后会有服务比较
                        result.setVerdict(AC);
                        result.setRunTime((int) (end - start));
                        result.setRunMemory(0);
                        result.setMsg(res.toString());
                    } else {

                        // 超时返回TLE
                        result.setVerdict(TLE);
                        result.setMsg("超时");
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setVerdict(RE);
        result.setMsg("系统错误");
        return result;
    }

    /**
     * 根据代码语言获取参数
     *
     * @param codeLang 代码语言
     * @return shell运行参数
     */
    private ArrayList<String> getArgs(String codeLang) {
        ArrayList<String> args = null;
        if ((CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(CPP_COMPILE);
        } else if ((JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(JAVA_RUN);
            args.add("-Xmx256m");
            args.add(JAVA_COMPILE);
        } else if ((PYTHON_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(PYTHON_RUN);
            args.add(PYTHON_COMPILE);
        }
        return args;
    }
}
