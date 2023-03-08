package com.hyperionoj.web.presentation.vo;

import com.hyperionoj.web.presentation.dto.CommentDTO;
import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/12
 */
@Data
@Builder
public class CommentVO {

    private String id;

    private String content;

    private String problemId;

    private UserVO author;

    private String parentId;

    private UserVO toUser;

    private Integer supportNumber;

    private String createDate;

    private Integer level;


}
