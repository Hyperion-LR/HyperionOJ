package com.hyperionoj.page.article.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ArticleComment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String content;

    private Long createTime;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer support;

    private Integer level;

    private Integer isDelete;

}
