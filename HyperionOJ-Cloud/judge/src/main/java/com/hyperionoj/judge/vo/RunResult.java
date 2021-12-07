package com.hyperionoj.judge.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
public class RunResult {

    private Integer submitId;

    private Integer problemId;

    private String verdict;

    private String msg;

    private Integer runTime;

    private Integer runMemory;

}
