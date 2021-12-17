package com.hyperionoj.common.pojo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/17
 */
@Data
public class Teacher {

    private Long id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
