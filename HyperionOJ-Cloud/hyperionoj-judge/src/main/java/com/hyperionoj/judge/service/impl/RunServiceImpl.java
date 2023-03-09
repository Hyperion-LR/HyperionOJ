package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.constants.Verdict;
import com.hyperionoj.judge.service.RunService;
import com.hyperionoj.judge.vo.RunResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.hyperionoj.judge.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Slf4j
@Service
public class RunServiceImpl implements RunService {

    @Resource
    private FilePath filePath;

    /**
     * 运行代码
     *
     * @param codeLang     代码语言
     * @param compiledFile 经过编译的代码文件
     * @param problemId    问题编号
     * @param runTime      运行时间限制
     * @param runMemory    运行内存限制
     * @param index        第几个测试点
     * @return 代码运行结果
     */
    @Override
    public RunResult run(String codeLang, String compiledFile, String problemId, Integer runTime, Integer runMemory, Integer index) {
        // 获取题目输入文件，并做好进程运行准备
        String inData = filePath.getProblem() + File.separator + problemId + File.separator + IN + File.separator + IN + index + TXT;
        RunResult result = RunResult.builder().build();
        ArrayList<String> args = getArgs(codeLang, runMemory, compiledFile);
        if (args == null) {
            result.setVerdict(Verdict.CE.getVerdict());
            result.setMsg(Verdict.CE.getMsg());
            return result;
        }
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(new File(compiledFile));
        try {
            // 正式开始启动程序
            Process process = processBuilder.start();

            // 记录启动时间
            long start = System.currentTimeMillis();

            inPut(process, inData);
            Future<String> res = outPut(process);
            Future<String> error = errorPut(process);

            if (!process.waitFor(runTime, TimeUnit.SECONDS)) {
                process.destroy();
                result.setVerdict(Verdict.TLE.getVerdict());
                result.setMsg("超时");
                return result;
            }

            // 这里不考虑结果对错之后会有服务比较
            if (StringUtils.isBlank(error.get())) {
                result.setVerdict(Verdict.AC.getVerdict());
                result.setRunTime((int) (System.currentTimeMillis() - start));
                result.setRunMemory(0);
                result.setMsg(res.get());
            } else {
                result.setVerdict(Verdict.RE.getVerdict());
                result.setMsg(error.get());
            }
            return result;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        result.setVerdict(Verdict.SE.getVerdict());
        result.setMsg(Verdict.SE.getVerdict());
        return result;
    }

    /**
     * 根据代码语言获取参数
     *
     * @param codeLang 代码语言
     * @return shell运行参数
     */
    private ArrayList<String> getArgs(String codeLang, Integer runMemory, String compileFile) {
        ArrayList<String> args = null;
        if ((CPP_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(compileFile + File.separator + CPP_COMPILE);
        } else if ((JAVA_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(JAVA_RUN);
            args.add("-Xmx" + runMemory + "m");
            args.add(JAVA_COMPILE);
        } else if ((PYTHON_LANG).equals(codeLang)) {
            args = new ArrayList<>();
            args.add(PYTHON_RUN);
            args.add(PYTHON_COMPILE);
        }
        return args;
    }

    /**
     * 向子进程输入数据
     */
    @Async("taskExecutor")
    public void inPut(Process process, String inData) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inData)));
             PrintStream ps = new PrintStream(process.getOutputStream())) {
            String tmp;
            // 向子进程输入数据
            while ((tmp = fileReader.readLine()) != null) {
                ps.println(tmp);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }


    /**
     * 获取子进程的错误信息
     */
    @Async("taskExecutor")
    public Future<String> errorPut(Process process) {
        String tmp;
        // 开始获取运行结果
        StringBuilder error = new StringBuilder();
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            // 获取错误信息，关闭已经不需要的刘流防止堵塞
            while ((tmp = errorReader.readLine()) != null) {
                error.append(tmp).append('\n');
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return new AsyncResult<>(error.toString());
    }


    /**
     * 获取子进程的输出信息
     */
    @Async("taskExecutor")
    public Future<String> outPut(Process process) {
        String tmp;
        // 开始获取运行结果
        StringBuilder res = new StringBuilder();
        try (BufferedReader dataReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            // 获取错误信息，关闭已经不需要的刘流防止堵塞
            while ((tmp = dataReader.readLine()) != null) {
                res.append(tmp).append('\n');
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return new AsyncResult<>(res.toString());
    }
}
