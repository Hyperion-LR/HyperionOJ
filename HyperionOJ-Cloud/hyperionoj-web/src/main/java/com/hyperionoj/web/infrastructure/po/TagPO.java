package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 标签PO类
 *
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tag")
public class TagPO {

    /**
     * 标签ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标签名
     */
    private String tagName;

}
