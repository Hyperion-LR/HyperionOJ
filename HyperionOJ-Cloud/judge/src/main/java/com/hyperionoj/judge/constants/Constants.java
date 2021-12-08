package com.hyperionoj.judge.constants;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface Constants {
    /**
     * 未知，开发时用于代表不确定
     */
    String UNKNOWN = "unknown";

    /**
     * 下划线: '_'
     */
    String UNDERLINE = "_";

    /**
     * 运行Java代码指令
     */
    String JAVA_RUN = "java";

    /**
     * 运行Python代码指令
     */
    String PYTHON_RUN = "python3";

    /**
     * CMD指令编译C++文件
     */
    String CPP_COMPILE_CMD = "g++";

    /**
     * CMD指令编译Java文件
     */
    String JAVA_COMPILE_CMD = "javac";

    /**
     * Java语言
     */
    String JAVA_LANG = "java";

    /**
     * Python语言后缀
     */
    String PYTHON_LANG = "python";

    /**
     * C++语言后缀
     */
    String CPP_LANG = "cpp";

    /**
     * 用户提交C++代码后保存至本地的名称
     */
    String CPP_FILENAME = "Main.cpp";

    /**
     * 用户提交Java代码后保存至本地的名称
     */
    String JAVA_FILENAME = "Main.java";

    /**
     * 用户提交Python代码后保存至本地的名称
     */
    String PYTHON_FILENAME = "Main.py";

    /**
     * 编译后的C++文件
     */
    String CPP_COMPILE = "Main.exe";

    /**
     * 编译后的Java文件
     */
    String JAVA_COMPILE = "Main";

    /**
     * 编译后的Python文件
     */
    String PYTHON_COMPILE = "Main.py";

    /**
     * 提交运行结果: 通过
     */
    String AC = "Accept";

    /**
     * 提交运行结果: 错误答案
     */
    String WA = "Wrong answer";

    /**
     * 提交运行结果: 运行超时
     */
    String TLE = "Time limit exceeded";

    /**
     * 提交运行结果: 内存超限
     */
    String MLE = "Memory limit exceeded";

    /**
     * 提交运行结果: 编译失败
     */
    String CE = "Compilation error";

    /**
     * 提交运行结果: 运行时错误
     */
    String RE = "Runtime Error";

    /**
     * 提交运行结果: 格式错误
     */
    String PE = "Presentation Error";


}
