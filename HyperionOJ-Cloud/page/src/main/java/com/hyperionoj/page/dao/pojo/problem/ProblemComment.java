package com.hyperionoj.page.dao.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ProblemComment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String content;

    private Long createTime;

    private Long problemId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

    private Integer supportNumber;

    private Integer isDelete;

}
