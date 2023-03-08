package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 管理员账号
 *
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin")
public class AdminPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String password;

    private Integer permissionLevel;

    private String salt;
}
