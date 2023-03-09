package com.hyperionoj.judge.vo;

import lombok.Data;

/**
 * 暂时不用
 *
 * @author Hyperion
 * @date 2021/12/9
 */
@Data
public class RunVO {

    private String codeLang;

    private String compiledFile;

    private String problemId;

    private Integer runTime;

    private Integer runMemory;

    private Integer index;

}
