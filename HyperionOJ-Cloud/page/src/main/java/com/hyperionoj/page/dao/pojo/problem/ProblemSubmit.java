package com.hyperionoj.page.dao.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ProblemSubmit {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long problemId;

    private Long authorId;

    private String codeLang;

    private String codeBody;

    private Integer status;

    private Integer runTime;

    private Integer runMemory;
}
