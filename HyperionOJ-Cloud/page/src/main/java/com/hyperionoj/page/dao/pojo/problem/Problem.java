package com.hyperionoj.page.dao.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class Problem {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private Long bodyId;

    private Integer problemLevel;

    private Long categoryId;

    private Integer acNumber;

    private Integer submitNumber;

    private Integer solutionNumber;

    private Integer commentNumber;

    private Integer runTime;

    private Integer runMemory;

    private Integer caseNumber;

}
