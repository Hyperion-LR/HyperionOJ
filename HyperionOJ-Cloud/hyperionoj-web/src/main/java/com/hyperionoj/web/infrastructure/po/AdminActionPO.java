package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 管理员行为
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_action")
public class AdminActionPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long adminId;

    private String adminAction;

    private Long actionTime;

    private Integer actionStatus;

}
