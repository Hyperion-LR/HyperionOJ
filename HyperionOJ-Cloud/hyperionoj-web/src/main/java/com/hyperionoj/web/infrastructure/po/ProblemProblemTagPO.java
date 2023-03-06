package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 题目标签关系类
 *
 * @author Hyperion
 * @date 2021/12/18
 */
@Data
public class ProblemProblemTagPO {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 题目ID
     */
    private Long problemId;

    /**
     * 标签ID
     */
    private Long tagId;

}
