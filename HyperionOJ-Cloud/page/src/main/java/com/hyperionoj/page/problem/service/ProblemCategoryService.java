package com.hyperionoj.page.problem.service;

import com.hyperionoj.page.common.dao.pojo.PageCategory;
import com.hyperionoj.page.common.vo.CategoryVo;

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
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 返回所有分类
     *
     * @return 返回所有分类
     */
    List<PageCategory> findAll();

    /**
     * 返回所有分类详细情况
     *
     * @return 返回所有分类的详细情况
     */
    List<PageCategory> findAllDetail();

    /**
     * 返回 categoryId 类别下所有文章
     *
     * @param categoryId 类别的id
     * @return 返回 categoryId 类别下所有文章
     */
    CategoryVo findAllDetailById(Long categoryId);
}
