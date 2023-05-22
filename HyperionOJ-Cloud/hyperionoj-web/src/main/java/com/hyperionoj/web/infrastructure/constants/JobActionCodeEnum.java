package com.hyperionoj.web.infrastructure.constants;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
public enum JobActionCodeEnum {
    /**
     * ######################################################
     * ##  作业启动相关：11XXX
     * ##  作业停止相关：13XXX
     * #####################################################
     */

    /**
     * 作业启动成功
     */
    START_PROCESS_SUCCESS(11000, "作业启动操作执行成功"),

    /**
     * 作业不存在
     */
    JOB_NO_EXIST(11001, "作业不存在"),

    /**
     * 作业尚未进行版本发布
     */
    NO_CURRENT_VERSION(11002, "作业尚未进行版本发布"),

    /**
     * ACTION参数错误
     */
    ACTION_PARAMS_ERROR(11003, "ACTION参数错误"),

    /**
     * 作业待上线版本不存在
     */
    CURRENT_VERSION_NO_EXISTS(11004, "作业待上线版本不存在"),

    /**
     * Flink配置版本不存在
     */
    FLINK_CONF_NO_EXISTS(11005, "Flink配置版本不存在"),

    /**
     * Flink版本已经被禁用
     */
    FLINK_VERSION_DISABLE(11006, "Flink版本已经被禁用"),

    /**
     * Flink配置集群不存在
     */
    FLINK_CLUSTER_NO_EXISTS(11007, "Flink配置集群不存在"),

    /**
     * Flink集群已经被禁用
     */
    FLINK_CLUSTER_DISABLE(11008, "Flink集群已经被禁用"),

    /**
     * 作业开发参数未配置/配置缺失
     */
    DEV_CONF_ERROR(11009, "作业开发参数未配置/配置缺失"),

    /**
     * 作业运行参数未配置/配置缺失
     */
    RUN_CONF_ERROR(11010, "作业运行参数未配置/配置缺失"),

    /**
     * 作业已处于运行状态
     */
    JOB_IS_RUNNING(11011, "作业已处于运行状态"),

    /**
     * 作业运行状态异常
     */
    STATUS_ERROR(11012, "作业运行状态异常"),

    /**
     * 作业无法并发启动
     */
    JOB_NO_CONCURRENT_STARTING(11013, "作业启动锁无法获取"),

    /**
     * 项目资源不足
     */
    RESOURCE_NOT_ENOUGH(11014, "项目资源不足"),

    /**
     * 作业启动中
     */
    JOB_STARTING(11015, "作业启动中"),

    /**
     * 作业启动失败
     */
    START_FAILED(11016, "作业启动失败"),

    /**
     * 作业提交成功
     */
    SUBMIT_SUCCESS(11017, "作业提交成功"),

    /**
     * 作业启动类型异常
     */
    SUBMIT_TYPE_ERROR(11018, "作业启动类型异常"),

    /**
     * 集群配置文件不存在
     */
    CLUSTER_CONF_NO_EXISTS(11019, "集群配置文件不存在"),

    /**
     * 作业Checkpoint记录未找到
     */
    CHECKPOINT_RECORD_NOT_FOUND(11020, "作业Checkpoint记录未找到"),

    /**
     * 作业Checkpoint文件未找到
     */
    CHECKPOINT_FILE_NOT_FOUND(11021, "作业Checkpoint文件未找到"),

    /**
     * 作业开发参数缺失
     */
    PARAMS_ERROR(11022, "作业开发参数缺失"),

    /**
     * 作业开发参数缺失
     */
    CLUSTER_NO_PERMISSION(11023, "集群被禁用"),

    /**
     * 作业停止成功
     */
    STOP_PROCESS_SUCCESS(12000, "作业停止操作执行成功"),

    /**
     * 作业已处于非运行状态
     */
    NO_RUNNING(12001, "作业已处于非运行状态"),

    /**
     * 作业线上版本丢失
     */
    ONLINE_VERSION_LOSE(12002, "作业线上版本丢失"),

    /**
     * 作业历史运行记录丢失
     */
    HISTORY_RECORD_LOSE(12003, "作业历史运行记录丢失"),

    /**
     * 作业停止失败
     */
    STOP_FAILED(12004, "作业停止失败"),

    /**
     * 作业停止中
     */
    STOPPING(12005, "作业停止中");
    private final Integer code;
    private final String message;

    JobActionCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
