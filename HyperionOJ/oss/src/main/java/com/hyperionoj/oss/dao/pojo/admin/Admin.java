package com.hyperionoj.oss.dao.pojo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class Admin {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
