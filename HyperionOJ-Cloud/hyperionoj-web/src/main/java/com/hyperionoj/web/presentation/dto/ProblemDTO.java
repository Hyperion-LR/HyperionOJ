package com.hyperionoj.web.presentation.dto;

import com.hyperionoj.web.presentation.vo.CategoryVO;
import com.hyperionoj.web.presentation.vo.TagVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProblemDTO {

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

    private CategoryDTO category;

    private List<TagDTO> tags;

}
