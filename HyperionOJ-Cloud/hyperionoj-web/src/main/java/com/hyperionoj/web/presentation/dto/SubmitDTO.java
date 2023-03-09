package com.hyperionoj.web.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDTO {

    private String id;

    private String username;

    private String problemId;

    private String authorId;

    private String codeLang;

    private String codeBody;

    private String status;

    private Integer caseNumber;

    private Integer runTime;

    private Integer runMemory;

    private String createTime;

}
