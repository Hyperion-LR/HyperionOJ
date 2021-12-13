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

    private Integer acNumber = 0;

    private Integer submitNumber = 0;

    private Integer solutionNumber = 0;

    private Integer commentNumber = 0;

    private Integer runTime = 1;

    private Integer runMemory = 256;

    private Integer caseNumber;

}
