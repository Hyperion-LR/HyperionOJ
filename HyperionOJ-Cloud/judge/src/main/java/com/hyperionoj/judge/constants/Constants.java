package com.hyperionoj.judge.constants;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface Constants {

    /**
     * 字符编码: gbk
     */
    String ENCODING_GBK = "gbk";

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
     * 输入数据文件
     */
    String IN = "in";

    /**
     * 答案数据文件
     */
    String OUT = "out";

    /**
     * 文本后缀
     */
    String TXT = ".txt";

    /**
     * 状态码成功
     */
    int SUCCESS_CODE = 200;

    /**
     * 用户密码加密盐
     */
    String SALT = "HyperionOJ";

    /**
     * 邮箱符号: '@'
     */
    Character AT = '@';

    /**
     * 分隔符英文句号: '.'
     */
    Character PERIOD = '.';

    /**
     * redis分隔符英文冒号: ':'
     */
    Character COLON = ':';

    /**
     * redis存token时的首部字符
     */
    String TOKEN = "TOKEN:";

    /**
     * 请求头里面的字段: UNDEFINED
     */
    String UNDEFINED = "UNDEFINED";

    /**
     * redis中验证码首部字符
     */
    String VER_CODE = "VER_CODE:";

    /**
     * 验证码长度为6
     */
    Integer CODE_LENGTH = 6;

    /**
     * 表示逗号 ","
     */
    String COMMA = ",";

    /**
     * 非法字符
     */
    String ILLEGAL_CHAR_SYSTEM = "system(";

    /**
     * 表示kafka请求judge服务运行代码主题
     */
    String KAFKA_TOPIC_SUBMIT = "SUBMIT";

    /**
     * 表示kafka从judge服务返回web服务运行结果消息主题
     */
    String KAFKA_TOPIC_SUBMIT_RESULT = "RESULT";

    /**
     * 表示kafka更新用户提交数量主题
     */
    String KAFKA_TOPIC_SUBMIT_PAGE = "SUBMIT_PAGE";

    /**
     * 表示提交状态
     */
    String AC = "AC";

    /**
     * 题目缓存名
     */
    String REDIS_KAY_PROBLEM_CACHE = "problem";

    /**
     * 题目类名
     */
    String PROBLEM_CONTROLLER = "ProblemController";

    /**
     * 获取题目列表方法名
     */
    String GET_PROBLEM_LIST = "getProblemList";

    /**
     * 获取题目方法名
     */
    String GET_PROBLEM_ID = "getProblemById";

    /**
     * 获取提交
     */
    String GET_SUBMIT = "getSubmit";

    /**
     * 点赞数
     */
    String PROBLEM_SUPPORT_COMMENT = "supportComment";

    /**
     * 获取评论列表
     */
    String GET_COMMENTS = "getComments";

    /**
     * 文章缓存类名
     */
    String ARTICLE_CONTROLLER = "ArticleController";

    /**
     * 文章缓存方法名
     */
    String FIND_ARTICLE_BY_ID = "findArticleById";

}
