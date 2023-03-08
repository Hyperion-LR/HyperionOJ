package com.hyperionoj.web.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {

    private String id;

    private String content;

    private String problemId;

    private UserDTO author;

    private String parentId;

    private UserDTO toUser;

    private Integer supportNumber;

    private String createDate;

    private Integer level;

}
