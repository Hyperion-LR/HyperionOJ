package com.hyperionoj.common.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class RegisterParam {

    private String id;

    private String name;

    private String password;

    private Integer permissionLevel;

}
