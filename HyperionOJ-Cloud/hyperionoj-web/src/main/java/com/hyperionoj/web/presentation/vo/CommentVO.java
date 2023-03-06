package com.hyperionoj.web.presentation.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/12
 */
@Data
public class CommentVO {

    private String id;

    private String content;

    private String problemId;

    private String articleId;

    private UserVO authorVo;

    private String parentId;

    private UserVO toUser;

    private Integer supportNumber;

    private String createDate;

    private Integer level;

}
