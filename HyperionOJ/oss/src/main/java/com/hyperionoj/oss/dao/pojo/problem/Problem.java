package com.hyperionoj.oss.dao.pojo.problem;

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

    private Integer level;

    private Long categoryId;

    private Integer acNumber;

    private Integer submitNumber;

    private Integer solutionNumber;

    private Integer commentNumber;

    private Long runTime;

    private Integer runMemory;

}
