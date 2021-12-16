package com.hyperionoj.page.problem.vo;

import com.hyperionoj.page.common.vo.TagVo;
import lombok.Data;

import java.util.List;

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

    private List<TagVo> tags;

}
