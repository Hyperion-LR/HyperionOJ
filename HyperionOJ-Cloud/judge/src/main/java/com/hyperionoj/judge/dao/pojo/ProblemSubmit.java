package com.hyperionoj.judge.dao.pojo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
public class ProblemSubmit {

    private Long id;

    private Long problemId;

    private Long authorId;

    private String codeLang;

    private String codeBody;

    private Integer status;

    private Integer runTime;

    private Integer runMemory;
}
