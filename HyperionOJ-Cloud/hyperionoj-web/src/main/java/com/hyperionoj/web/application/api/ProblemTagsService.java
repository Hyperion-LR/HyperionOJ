package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.TagPO;
import com.hyperionoj.web.presentation.vo.TagVO;

import java.util.List;

/**
 * @author Hyperion
 */
public interface ProblemTagsService {

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
