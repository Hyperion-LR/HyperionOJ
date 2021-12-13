package com.hyperionoj.page.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/12
 */
@Data
public class CommentVo {

    private String id;

    private String content;

    private String problemId;

    private String authorId;

    private String parentId;

    private String toUid;

    private Integer supportNumber;

    private Integer level;

}
