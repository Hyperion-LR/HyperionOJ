package com.hyperionoj.web.presentation.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
@Data
public class CommentParamVO {

    private String id;

    private String articleId;

    private String content;

    private String parent;

    private String toUserId;

}