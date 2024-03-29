package com.hyperionoj.web.presentation.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitVO {

    private String id;

    private String authorId;

    private String problemId;

    private String codeLang;

    private String codeBody;

    private Integer runTime;

    private Integer runMemory;

    private Integer caseNumber;

    private String verdict;

    private String createTime;

}