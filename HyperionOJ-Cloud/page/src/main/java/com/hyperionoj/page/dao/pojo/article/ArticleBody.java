package com.hyperionoj.page.dao.pojo.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class ArticleBody {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String content;

    private String contentHtml;

    private String articleId;

}
