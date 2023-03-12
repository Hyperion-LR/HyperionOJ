package com.hyperionoj.web.infrastructure.constants;

/**
 * 表示job的运行状态
 * @author Hyperion
 */
public enum JobStatusEnum {

    /**
     * 作业新建
     */
    NEW("new"),
    /**
     * 作业启动中
     */
    START("start"),
    /**
     * 作业运行中
     */
    RUNNING("running"),
    /**
     * 作业停止中
     */
    STOP("stop"),
    /**
     * 作业运行失败
     */
    FAIL("fail"),
    /**
     * 作业结束运行
     */
    END("end");

    private String status;

    JobStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
