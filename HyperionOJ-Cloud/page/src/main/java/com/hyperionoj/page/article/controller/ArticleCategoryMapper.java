package com.hyperionoj.page.article.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.article.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
@RestController
@RequestMapping("/article/category")
public class ArticleCategoryMapper {

    @Resource
    private ArticleCategoryService articleCategoryService;

    @GetMapping
    public Result category() {
        return Result.success(articleCategoryService.findAll());
    }

    @GetMapping("/detail")
    public Result categoryDetail() {
        return Result.success(articleCategoryService.findAllDetail());
    }

    @GetMapping("/detail/{id}")
    public Result categoryDetail(@PathVariable("id") Long categoryId) {
        Object data = articleCategoryService.findAllDetailById(categoryId);
        if (data == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return Result.success(data);
        }
    }

}
