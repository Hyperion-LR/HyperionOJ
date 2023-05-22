package com.hyperionoj.web.infrastructure.constants;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
public enum JobAlarmEnum {
    START_ERROR("START_ERROR", "作业启动失败"),
    RUN_ERROR("RUN_ERROR", "作业运行出错"),
    RUN_STOP("RUN_STOP", "作业运行停止"),
    END("END", "作业运行完成")
    ;

    private String level;

    private String message;

    JobAlarmEnum(String level, String message){
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
