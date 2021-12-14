package com.hyperionoj.common.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class ProblemVo {

    private String id;

    private String title;

    private String bodyId;

    private Integer problemLevel;

    private String categoryId;

    private Integer acNumber;

    private Integer submitNumber;

    private Integer solutionNumber;

    private Integer commentNumber;

    private Integer caseNumber;

    private Integer runMemory;

    private Integer runTime;

    private ProblemBodyVo problemBodyVo;

}