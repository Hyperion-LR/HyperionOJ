package com.hyperionoj.common.vo.page;

import lombok.Data;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
public class ArticleVo {

    private String id;

    private String title;

    private SysUserVo author;

    private String summary;

    /**
     * 创建时间
     */
    private String createDate;

    private Integer commentCounts = 0;

    private Integer viewCounts = 0;

    private Integer support = 0;

    private Integer weight = 0;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
