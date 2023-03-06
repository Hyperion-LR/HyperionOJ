package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 管理员账号
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class AdminPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
