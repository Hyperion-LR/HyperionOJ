package com.hyperionoj.common.vo.page;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class RunResult {

    private String authorId;

    private Long submitId;

    private Long problemId;

    private String verdict;

    private String msg;

    private Integer runTime;

    private Integer runMemory;

    private String submitTime;

}