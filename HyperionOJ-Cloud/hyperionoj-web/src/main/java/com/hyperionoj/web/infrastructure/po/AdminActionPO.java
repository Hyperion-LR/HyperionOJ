package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 管理员行为
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class AdminActionPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long adminId;

    private String adminAction;

    private Long actionTime;

    private Integer actionStatus;

}
