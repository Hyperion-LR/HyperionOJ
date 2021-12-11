package com.hyperionoj.common.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
public class AdminVo {

    private String id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
