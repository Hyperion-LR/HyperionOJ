package com.hyperionoj.page.vo;

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

    private Integer acNumber = 0;

    private Integer submitNumber = 0;

    private Integer solutionNumber = 0;

    private Integer commentNumber = 0;

    private Integer caseNumber;

    private Integer runMemory;

    private Integer runTime;

    private ProblemBodyVo problemBodyVo;

}
