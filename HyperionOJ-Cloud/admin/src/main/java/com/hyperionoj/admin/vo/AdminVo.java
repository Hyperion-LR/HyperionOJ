package com.hyperionoj.admin.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class AdminVo {

    private Long id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
