package com.hyperionoj.common.pojo.bo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/8
 */
@Data
public class SysUser {

    private Long id;

    private String username;

    private String avatar;

    private String password;

    private String studentNumber;

    private String mail;

    private Long createTime;

    private Long lastLogin;

    private Integer problemAcNumber;

    private Integer problemSubmitNumber;

    private Integer problemSubmitAcNumber;

    private String salt;

    private Integer status;
}
