package com.hyperionoj.web.presentation.dto;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
@Data
public class UpdatePasswordParam {

    /**
     * 邮箱
     */
    private String userMail;

    /**
     * 已经发送的验证码
     */
    private String code;

    /**
     * 更新的密码
     */
    private String password;

}
