package com.hyperionoj.common.vo.page;

import com.hyperionoj.common.pojo.SysUser;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
public class SysUserVo {

    private String id;

    private String username;

    private String mail;

    private String studentNumber;

    public static SysUserVo userToVo(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setId(sysUser.getId().toString());
        sysUserVo.setUsername(sysUser.getUsername());
        sysUserVo.setMail(sysUser.getMail());
        return sysUserVo;
    }

}