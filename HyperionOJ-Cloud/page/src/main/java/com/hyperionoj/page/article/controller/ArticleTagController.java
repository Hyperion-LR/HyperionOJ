package com.hyperionoj.page.article.controller;

import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.article.service.ArticleTagsService;
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
@RequestMapping("/article/tag")
public class ArticleTagController {

    @Resource
    private ArticleTagsService articleTagsService;

    @GetMapping
    public Result tags() {
        return Result.success(articleTagsService.findAll());
    }

    @GetMapping("/detail")
    public Result tagsDetail() {
        return Result.success(articleTagsService.findAllDetail());
    }

    @GetMapping("/detail/{id}")
    public Result tagDetail(@PathVariable("id") Long tagId) {
        Object allDetailById = articleTagsService.findAllDetailById(tagId);
        if (allDetailById == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(allDetailById);
    }

    @GetMapping("/hot")
    public Result hot() {
        int limit = 3;
        return Result.success(articleTagsService.hots(limit));
    }
}
