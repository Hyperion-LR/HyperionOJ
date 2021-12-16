package com.hyperionoj.page.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.page.article.service.ArticleCategoryService;
import com.hyperionoj.page.common.dao.mapper.CategoryMapper;
import com.hyperionoj.page.common.dao.pojo.PageCategory;
import com.hyperionoj.page.common.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Hyperion
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 通过 id 查找分类
     *
     * @param categoryId 分类id
     * @return 分类
     */
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        PageCategory category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        if (category != null) {
            categoryVo.setId(categoryId.toString());
            categoryVo.setCategoryName(category.getCategoryName());
            categoryVo.setDescription(category.getDescription());
        }
        return categoryVo;
    }

    /**
     * 返回所有分类
     *
     * @return 返回所有分类
     */
    @Override
    public List<PageCategory> findAll() {
        LambdaQueryWrapper<PageCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PageCategory::getId, PageCategory::getCategoryName);
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 返回所有分类详细情况
     *
     * @return 返回所有分类的详细情况
     */
    @Override
    public List<PageCategory> findAllDetail() {
        LambdaQueryWrapper<PageCategory> queryWrapper = new LambdaQueryWrapper<>();
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 返回 categoryId 类别下所有文章
     *
     * @param categoryId 类别的id
     * @return 返回 categoryId 类别下所有文章
     */
    @Override
    public CategoryVo findAllDetailById(Long categoryId) {
        PageCategory category = categoryMapper.selectById(categoryId);
        if (category == null) {
            return null;
        }
        return copy(category);
    }

    private List<CategoryVo> copyList(List<PageCategory> categories) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (PageCategory category : categories) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy(PageCategory category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
