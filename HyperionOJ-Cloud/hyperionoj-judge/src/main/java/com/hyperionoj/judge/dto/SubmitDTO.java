package com.hyperionoj.judge.dto;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
public class SubmitDTO {

    private String authorId;

    private String problemId;

    private String codeLang;

    private String codeBody;

    private Integer runTime;

    private Integer runMemory;

    private Integer caseNumber;

}