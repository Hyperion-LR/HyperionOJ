package com.hyperionoj.web.presentation.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
public class AdminVO {

    private String id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
