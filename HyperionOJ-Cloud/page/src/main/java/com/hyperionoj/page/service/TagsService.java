package com.hyperionoj.page.service;


import com.hyperionoj.page.dao.pojo.PageTag;
import com.hyperionoj.page.vo.TagVo;

import java.util.List;

/**
 * @author Hyperion
 */
public interface TagsService {

    /**
     * 根据文章 articleId 返回标签
     *
     * @param articleId 文章id
     * @return 标签链表
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 返回 limit 数量的数量最多的标签
     *
     * @param limit 返回数量
     * @return 返回结果
     */
    List<PageTag> hots(Integer limit);

    /**
     * 返回所有标签
     *
     * @return 所有标签
     */
    List<TagVo> findAll();

    /**
     * 返回所有标签详情
     *
     * @return 所有标签详情
     */
    List<TagVo> findAllDetail();

    /**
     * 返回标签详情
     *
     * @param tagId 标签id
     * @return id标签详情
     */
    TagVo findAllDetailById(Long tagId);

    /**
     * 像文章添加 id
     *
     * @param articleId 文章 id
     * @param tagId     标签 id
     */
    void addTag(Long articleId, Long tagId);

}
