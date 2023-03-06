package com.hyperionoj.web.presentation.vo;


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
    USER_FREEZE(508, "账号已经冻结");

    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
