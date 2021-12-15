package com.hyperionoj.page.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.service.ArticleService;
import com.hyperionoj.page.service.CategoryService;
import com.hyperionoj.page.service.TagsService;
import com.hyperionoj.page.vo.params.ArticleParam;
import com.hyperionoj.page.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagsService tagsService;

    /**
     * 首页 文章列表
     *
     * @param pageParams 分页参数
     * @return 返回查询分页
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return Result.success(articleService.listArticle(pageParams));
    }

    @RequestMapping("/new")
    public Result newArticle(@RequestBody PageParams pageParams) {
        return Result.success(articleService.newArticle(pageParams.getPageSize()));
    }

    @RequestMapping("/hot")
    public Result hotArticle(@RequestBody PageParams pageParams) {
        return Result.success(articleService.hotArticle(pageParams.getPageSize()));
    }

    @PostMapping("/listArchives")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        Object data = articleService.findArticleById(articleId);
        if (data == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return Result.success(data);
        }
    }

    @PostMapping("/publish")
    public Result articlePublish(@RequestBody ArticleParam articleParam) {
        return Result.success(articleService.publish(articleParam));
    }

    @GetMapping("/category")
    public Result categorys() {
        return Result.success(categoryService.findAll());
    }

    @GetMapping("/category/detail")
    public Result categorysDetail() {
        return Result.success(categoryService.findAllDetail());
    }

    @GetMapping("/category/detail/{id}")
    public Result categoryDetail(@PathVariable("id") Long categoryId) {
        Object data = categoryService.findAllDetailById(categoryId);
        if (data == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return Result.success(data);
        }
    }

    @GetMapping("/tag")
    public Result tags() {
        return Result.success(tagsService.findAll());
    }

    @GetMapping("/tag/detail")
    public Result tagsDetail() {
        return Result.success(tagsService.findAllDetail());
    }

    @GetMapping("/tag/detail/{id}")
    public Result tagDetail(@PathVariable("id") Long tagId) {
        Object allDetailById = tagsService.findAllDetailById(tagId);
        if (allDetailById == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(allDetailById);
    }

    @GetMapping("/tag/hot")
    public Result hot() {
        int limit = 3;
        return Result.success(tagsService.hots(limit));
    }

}
