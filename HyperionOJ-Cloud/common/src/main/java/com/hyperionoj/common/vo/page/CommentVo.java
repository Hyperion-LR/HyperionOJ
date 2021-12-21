package com.hyperionoj.common.vo.page;

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

    private String articleId;

    private SysUserVo authorVo;

    private String parentId;

    private SysUserVo toUser;

    private Integer supportNumber;

    private String createDate;

    private Integer level;

}
