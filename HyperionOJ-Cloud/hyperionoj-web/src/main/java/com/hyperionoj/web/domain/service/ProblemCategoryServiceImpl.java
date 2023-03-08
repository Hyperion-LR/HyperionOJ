package com.hyperionoj.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.ProblemCategoryService;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.infrastructure.mapper.CategoryMapper;
import com.hyperionoj.web.infrastructure.po.CategoryPO;
import com.hyperionoj.web.presentation.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Hyperion
 */
@Service
public class ProblemCategoryServiceImpl implements ProblemCategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 通过 id 查找分类
     *
     * @param categoryId 分类id
     * @return 分类
     */
    @Override
    public CategoryVO findCategoryById(Long categoryId) {
        return MapStruct.toVO(categoryMapper.selectById(categoryId));
    }

    /**
     * 返回所有分类
     *
     * @return 返回所有分类
     */
    @Override
    public List<CategoryPO> findAll() {
        LambdaQueryWrapper<CategoryPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CategoryPO::getId, CategoryPO::getCategoryName);
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 返回所有分类详细情况
     *
     * @return 返回所有分类的详细情况
     */
    @Override
    public List<CategoryPO> findAllDetail() {
        LambdaQueryWrapper<CategoryPO> queryWrapper = new LambdaQueryWrapper<>();
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 返回 categoryId 类别下所有文章
     *
     * @param categoryId 类别的id
     * @return 返回 categoryId 类别下所有文章
     */
    @Override
    public CategoryVO findAllDetailById(Long categoryId) {
        return MapStruct.toVO(categoryMapper.selectById(categoryId));
    }



}
