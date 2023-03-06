package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.PageTagPO;
import com.hyperionoj.web.presentation.vo.TagVO;

import java.util.List;

/**
 * @author Hyperion
 */
public interface ProblemTagsService {

    /**
     * 根据文章 articleId 返回标签
     *
     * @param articleId 文章id
     * @return 标签链表
     */
    List<TagVO> findTagsByArticleId(Long articleId);

    /**
     * 返回 limit 数量的数量最多的标签
     *
     * @param limit 返回数量
     * @return 返回结果
     */
    List<PageTagPO> hots(Integer limit);

    /**
     * 返回所有标签
     *
     * @return 所有标签
     */
    List<TagVO> findAll();

    /**
     * 返回所有标签详情
     *
     * @return 所有标签详情
     */
    List<TagVO> findAllDetail();

    /**
     * 返回标签详情
     *
     * @param tagId 标签id
     * @return id标签详情
     */
    TagVO findAllDetailById(Long tagId);

}
