package com.hyperionoj.common.pojo.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Data
public class Result {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 定义信息
     */
    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 错误结果
     *
     * @param code 状态码
     * @param msg  错误信息
     * @return Result
     */
    public static Result fail(Integer code, String msg) {
        return new Result(code, null, msg);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(errorCode.getCode(), null, errorCode.getMsg());
    }

    /**
     * 正确返回
     *
     * @param data 返回数据
     * @return Result
     */
    public static Result success(Object data) {
        return new Result(200, data, null);
    }

}
