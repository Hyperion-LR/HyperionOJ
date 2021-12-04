package com.hyperionoj.oss.dao.pojo.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class Article {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private Long authorId;

    private String summary;

    private Long bodyId;

    private Long categoryId;

    private Long createTime;

    private Integer commentCount;

    private Integer viewCount;

    private Integer weight;

    private Integer isSolution;

    private Long problemId;

    private Integer delete;

}
