package com.hyperionoj.oss.dao.pojo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class AdminPermission {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String permissionName;

    private String path;

    private Integer level;

}