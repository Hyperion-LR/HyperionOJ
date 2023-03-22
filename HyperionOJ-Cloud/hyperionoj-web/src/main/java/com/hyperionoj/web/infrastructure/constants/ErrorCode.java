package com.hyperionoj.web.infrastructure.constants;


/**
 * 用来返回系统内部状态码
 *
 * @author Hyperion
 */
public enum ErrorCode {
    /**
     * 用来返回系统内部状态码
     */
    SYSTEM_ERROR(501, "系统错误"),
    CODE_ERROR(501, "验证码错误"),
    PARAMS_ERROR(502, "参数有误"),
    TOKEN_ERROR(503, "token不合法"),
    ACCOUNT_EXIST(504, "账号已存在"),
    NO_PERMISSION(505, "无访问权限"),
    SESSION_TIME_OUT(506, "会话超时"),
    NO_LOGIN(507, "未登录"),
    USER_FREEZE(508, "账号已经冻结"),
    JOB_USER_RESOURCE_FULL(999, "用户资源配额已满"),
    JOB_USER_SQL_CHECK(999, "SQL语法错误");

    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
