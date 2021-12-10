package com.hyperionoj.oss.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class RegisterAdminParam {

    private String id;

    private String name;

    private String password;

    private Integer permissionLevel;

}
