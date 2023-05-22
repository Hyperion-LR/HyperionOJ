package com.hyperionoj.web.infrastructure.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2023/3/15
 */
public class SubmitUtil {

    public static String[] getParamStrFromRuntime(String path, Integer jmMem, Integer tmMem, Integer tmSlot, Integer parallelism, String jarName, String mainClass, String mainArgs) {
        List<String> args = new ArrayList<>();
        args.add(path);
        args.add("run");
        args.add("-m");
        args.add("yarn-cluster");
        args.add("-yjm");
        args.add(jmMem.toString());
        args.add("-ytm");
        args.add(tmMem.toString());
        args.add("-ys");
        args.add(tmSlot.toString());
        if (!Objects.isNull(parallelism)) {
            args.add("-p");
            args.add(parallelism.toString());
        }
        args.add("-c");
        args.add(mainClass);
        args.add("-d");
        if (!jarName.contains(JOB_JAR_NAME)) {
            jarName = jarName + JOB_JAR_NAME;
        }
        args.add(jarName);
        if (!Objects.isNull(mainArgs)) {
            args.add(parseFlinkMainArgs(mainArgs));
        }
        return args.toArray(String[]::new);
    }

    public static String[] getParamStopJarJob(String yarnPath, String flinkJobId) {
        List<String> args = new ArrayList<>();
        //使用同目录下的yarn
        args.add(yarnPath);
        args.add("application");
        args.add("-kill");
        args.add(flinkJobId);
        return args.toArray(String[]::new);
    }

    public static String parseFlinkMainArgs(String mainArgs) {
        if (Objects.isNull(mainArgs)) {
            return "";
        }

        StringBuilder sbf = new StringBuilder();
        String[] mains = mainArgs.trim()
                .replaceAll("\n", " ")
                .replaceAll("\\s", " ")
                .replaceAll("\t", " ")
                .split(" ");
        for (String main : mains) {
            if (main.trim().length() == 0) {
                continue;
            }
            main = main.trim();

            if (main.startsWith("'") || main.endsWith("'") || main.startsWith("-")) {
                sbf.append(" ").append(main);
                continue;
            }

            sbf.append(" ").append("'").append(main).append("'");
        }
        return sbf.toString().trim().replaceAll("\n", " ").replaceAll(" ", "#");
    }

    public static String getJobIdFromLog(String log) {
        return parseData(log, JobID_PATTERN);
    }

    public static String getApplicationIdFromLog(String log) {
        return parseData(log, Application_PATTERN);
    }

    /**
     * 根据作业日志解析作业Web服务地址
     *
     * @param log 作业日志
     * @return Web服务地址
     */
    public static String getJobUrlFromLog(String log) {
        return parseData(log, URL_PATTERN);
    }

    /**
     * 规则对象解析类
     *
     * @param log      日志
     * @param parttern 模式对象
     * @return 规则对象
     */
    public static String parseData(String log, String parttern) {
        for (String l : log.split("\n")) {
            if (l.contains(parttern)) {
                String[] data = l.split(parttern);
                return data[1];
            }
        }
        return null;
    }


}
