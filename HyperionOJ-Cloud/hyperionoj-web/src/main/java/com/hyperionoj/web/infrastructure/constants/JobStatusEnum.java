package com.hyperionoj.web.infrastructure.constants;

/**
 * 表示job的运行状态
 * @author Hyperion
 */
public enum JobStatusEnum {

    /**
     * 作业新建
     */
    NEW("NEW"),
    /**
     * 作业启动中
     */
    START("START"),
    /**
     * 作业运行中
     */
    RUNNING("RUNNING"),
    /**
     * 作业停止中
     */
    STOP("STOP"),
    /**
     * 作业运行失败
     */
    FAILED("FAILED"),
    /**
     * 作业结束运行
     */
    END("END"),

    FINISHED("FINISHED");

    private String status;

    JobStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static JobStatusEnum getJobStatusEnum(String status){
        for(JobStatusEnum jobStatusEnum : JobStatusEnum.values()){
            if(jobStatusEnum.getStatus().equals(status)){
                return jobStatusEnum;
            }
        }
        return null;
    }
}
