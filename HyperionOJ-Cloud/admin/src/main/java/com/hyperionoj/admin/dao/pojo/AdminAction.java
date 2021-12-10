package com.hyperionoj.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class AdminAction {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long adminId;

    private String adminAction;

    private Long actionTime;

    private Integer actionStatus;

}
