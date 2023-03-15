package com.hyperionoj.web.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * @author Hyperion
 * @date 2023/3/15
 */
@Slf4j
public class ExecuteCommandUtil {
    public int exitCode = 0;
    public String stdOut;
    public String stdError;

    /**
     * 执行系统命令, 返回执行结果, 目前是读取所有的输出后返回,并不是实时输出日志
     *
     * @param command 需要执行的命令
     * @param dirPath 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public int execCommand(String[] command, String dirPath) throws Exception {
        StringBuilder stdOutResult = new StringBuilder();
        StringBuilder stdErrorResult = new StringBuilder();

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            File dir = new File(dirPath);
            process = Runtime.getRuntime().exec(command, null, dir);

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                stdOutResult.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                stdErrorResult.append(line).append('\n');
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();

            exitCode = process.exitValue();
            stdOut = stdOutResult.toString();
            stdError = stdErrorResult.toString();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.toString());
        }
        finally {
            closeStream(bufrIn);
            closeStream(bufrError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        // 返回错误码
        return exitCode;
    }

    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }
}
