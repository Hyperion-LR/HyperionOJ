package com.hyperionoj.web.presentation.dto.param;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class RegisterParam {

    private String id;

    private String username;

    private String password;

    private String mail;

    private String verCode;

}
