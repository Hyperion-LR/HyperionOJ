package com.hyperionoj.page.dao.pojo.article;

import lombok.Data;

/**
 * @author Hyperion
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
