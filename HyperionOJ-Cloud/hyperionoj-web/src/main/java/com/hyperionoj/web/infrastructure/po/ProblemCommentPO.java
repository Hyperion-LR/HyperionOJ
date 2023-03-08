package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 题目评论
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("problem_comment")
public class ProblemCommentPO {

    /**
     * 题目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer supportNumber;

    /**
     * 评论时间
     */
    private Long createTime;

    /**
     * 题目ID
     */
    private Long problemId;

    /**
     * 评论者ID
     */
    private Long authorId;

    /**
     * 对某评论的回复评论，可为空
     */
    private Long parentId;

    /**
     * 给谁的回复评论
     */
    private Long toUid;

    /**
     * 评论楼
     */
    private Integer level;

    /**
     * 是否删除
     */
    private Integer isDelete;

}
