package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.CategoryPO;
import com.hyperionoj.web.presentation.vo.CategoryVO;

import java.util.List;


/**
 * @author Hyperion
 */
public interface ProblemCategoryService {
    /**
     * 通过 id 查找分类
     *
     * @param categoryId 分类id
     * @return 分类
     */
    CategoryVO findCategoryById(Long categoryId);

    /**
     * 返回所有分类
     *
     * @return 返回所有分类
     */
    List<CategoryPO> findAll();

    /**
     * 返回所有分类详细情况
     *
     * @return 返回所有分类的详细情况
     */
    List<CategoryPO> findAllDetail();

    /**
     * 返回 categoryId 类别下所有文章
     *
     * @param categoryId 类别的id
     * @return 返回 categoryId 类别下所有文章
     */
    CategoryVO findAllDetailById(Long categoryId);
}
