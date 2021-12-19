package com.hyperionoj.common.vo.params;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
@Data
public class CommentParam {

    private String id;

    private String articleId;

    private String content;

    private String parent;

    private String toUserId;

}