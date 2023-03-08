package com.hyperionoj.web.presentation.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
@Builder
public class ProblemVO {

    private String id;

    private String title;

    private Integer problemLevel;

    private Integer acNumber;

    private Integer submitNumber;

    private Integer commentNumber;

    private Integer caseNumber;

    private Integer runMemory;

    private Integer runTime;

    private String problemBody;

    private String problemBodyHtml;

    private CategoryVO category;

    private List<TagVO> tags;

}
