package com.hyperionoj.web.presentation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class ProblemVO {

    private String id;

    private String title;

    private String bodyId;

    private Integer problemLevel;

    private Integer acNumber = 0;

    private Integer submitNumber = 0;

    private Integer solutionNumber = 0;

    private Integer commentNumber = 0;

    private Integer caseNumber;

    private Integer runMemory;

    private Integer runTime;

    private ProblemBodyVO problemBodyVo;

    private CategoryVO category;

    private List<TagVO> tags;

}
