package com.hyperionoj.oss.dao.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class SysUser {

    @TableId(type = IdType.ASSIGN_ID)
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
