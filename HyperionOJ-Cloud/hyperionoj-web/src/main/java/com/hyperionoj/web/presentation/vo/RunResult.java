package com.hyperionoj.web.presentation.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunResult {

    private String authorId;

    private String submitId;

    private String problemId;

    private String verdict;

    private String msg;

    private Integer runTime;

    private Integer runMemory;

    private String submitTime;

}