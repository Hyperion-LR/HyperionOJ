package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 标签PO类
 *
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class PageTagPO {

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
