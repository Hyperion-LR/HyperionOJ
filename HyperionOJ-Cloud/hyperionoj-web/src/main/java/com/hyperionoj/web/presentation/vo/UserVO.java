package com.hyperionoj.web.presentation.vo;

import com.hyperionoj.web.infrastructure.po.UserPO;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
public class UserVO {

    private String id;

    private String username;

    private String mail;

    private String studentNumber;

    public static UserVO userToVo(UserPO sysUser) {
        if (sysUser == null) {
            return null;
        }
        UserVO userVo = new UserVO();
        userVo.setId(sysUser.getId().toString());
        userVo.setUsername(sysUser.getUsername());
        userVo.setMail(sysUser.getMail());
        return userVo;
    }

}