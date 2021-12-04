package com.hyperionoj.oss.dao.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class SysTeacher {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String avatar;

    private String password;

    private String teacherNumber;

    private String mail;

    private String department;

    private Long createTime;

    private Long lastLogin;

    private String salt;

    private Integer status;

}
