package com.hyperionoj.web.infrastructure.constants;

/**
 * 作业状态事件枚举类
 */
public enum JobEventEnum {

    /**
     * 启动中
     */
    STARTING("STARTING", "启动中"),

    /**
     * 启动失败
     */
    START_FAILED("START_FAILED", "启动失败"),

    /**
     * 运行失败
     */
    RUN_FAILED("RUN_FAILED", "运行失败"),

    /**
     * 运行异常
     */
    RUN_EXCEPTION("RUN_EXCEPTION", "运行异常"),

    /**
     * 作业发生重启
     */
    FAILOVER("FAILOVER", "作业重启"),

    /**
     * 作业已提交启动
     */
    START_JOB_SUBMIT_SUCCESS("START_JOB_SUBMIT_SUCCESS", "作业已提交启动"),

    /**
     * 作业启动成功
     */
    START_JOB_SUCCESS("START_JOB_SUCCESS", "作业启动成功"),

    // ########################################################

    /**
     * Ingress映射关系创建异常
     */
    INGRESS_CREATE_ERROR("INGRESS_CREATE_ERROR", "Ingress映射关系创建异常"),

    /**
     * Ingress映射关系删除异常
     */
    INGRESS_DELETE_ERROR("INGRESS_DELETE_ERROR", "Ingress映射关系删除异常"),


    /**
     * Deployment删除异常
     */
    DELETE_DEPLOYMENT_ERROR("DELETE_DEPLOYMENT_ERROR", "Deployment删除异常"),

    // ########################################################

    /**
     * 停止中·中间态
     */
    STOPPING("STOPPING", "停止中"),

    /**
     * 作业停止失败
     */
    STOP_FAILED("STOP_FAILED", "作业停止失败"),

    /**
     * 停止作业成功
     */
    STOP_JOB_SUCCESS("STOP_JOB_SUCCESS", "停止作业成功");

    private final String type;
    private final String description;

    JobEventEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
