package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目PO类
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("")
public class ProblemPO {

    /**
     * 题目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目体
     */
    private String problemBody;

    /**
     * 题目体（HTML格式）
     */
    private String problemBodyHtml;

    /**
     * 题目难度
     */
    private Integer problemLevel;

    /**
     * 后台测试点数量
     */
    private Integer caseNumber;

    /**
     * 题目分类
     */
    private Long categoryId;

    /**
     * 题目总AC次数
     */
    private Integer acNumber;

    /**
     * 题目总提交次数
     */
    private Integer submitNumber;

    /**
     * 题解数量
     */
    private Integer solutionNumber;

    /**
     * 评论数量
     */
    private Integer commentNumber;

    /**
     * 运行时间限制
     */
    private Integer runTime;

    /**
     * 运行内存限制
     */
    private Integer runMemory;

}
