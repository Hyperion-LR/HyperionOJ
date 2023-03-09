package com.hyperionoj.judge.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
@Builder
public class RunResult {

    private String authorId;

    private Long submitId;

    private String submitTime;

    private Long problemId;

    private String verdict;

    private String msg;

    private Integer runTime;

    private Integer runMemory;

}
