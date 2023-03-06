package com.hyperionoj.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.ProblemCategoryService;
import com.hyperionoj.web.infrastructure.mapper.CategoryMapper;
import com.hyperionoj.web.infrastructure.po.PageCategoryPO;
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
        PageCategoryPO category = categoryMapper.selectById(categoryId);
        CategoryVO categoryVo = new CategoryVO();
        if (category != null) {
            BeanUtils.copyProperties(category, categoryVo);
        }
        return categoryVo;
    }

    /**
     * 返回所有分类
     *
     * @return 返回所有分类
     */
    @Override
    public List<PageCategoryPO> findAll() {
        LambdaQueryWrapper<PageCategoryPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PageCategoryPO::getId, PageCategoryPO::getCategoryName);
        return categoryMapper.selectList(queryWrapper);
    }

    /**
     * 返回所有分类详细情况
     *
     * @return 返回所有分类的详细情况
     */
    @Override
    public List<PageCategoryPO> findAllDetail() {
        LambdaQueryWrapper<PageCategoryPO> queryWrapper = new LambdaQueryWrapper<>();
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
        PageCategoryPO category = categoryMapper.selectById(categoryId);
        if (category == null) {
            return null;
        }
        return copy(category);
    }

    private List<CategoryVO> copyList(List<PageCategoryPO> categories) {
        List<CategoryVO> categoryVoList = new ArrayList<>();
        for (PageCategoryPO category : categories) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVO copy(PageCategoryPO category) {
        CategoryVO categoryVo = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
