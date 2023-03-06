package com.hyperionoj.web.presentation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class ArticleVO {

    private String id;

    private String title;

    private UserVO author;

    private String summary;

    /**
     * 创建时间
     */
    private String createDate;

    private Integer commentCounts = 0;

    private Integer viewCounts = 0;

    private Integer support = 0;

    private Integer weight = 0;

    private ArticleBodyVO body;

    private List<TagVO> tags;

    private CategoryVO category;

}
