package com.hyperionoj.page.common.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class PageCategory {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String categoryName;

    private String description;

}
