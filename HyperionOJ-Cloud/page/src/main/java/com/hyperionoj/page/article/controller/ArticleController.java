package com.hyperionoj.page.article.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.cache.Cache;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.article.service.ArticleService;
import com.hyperionoj.page.article.vo.ArticleVo;
import com.hyperionoj.page.article.vo.params.ArticleParam;
import com.hyperionoj.page.article.vo.params.CommentParam;
import com.hyperionoj.page.common.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 首页 文章列表
     *
     * @param pageParams 分页参数
     * @return 返回查询分页
     */
    @GetMapping("/articles")
    public Result listArticle(@RequestParam("page") String pageParams) {
        return Result.success(articleService.listArticle(JSONObject.parseObject(pageParams, PageParams.class)));
    }

    @GetMapping("/new")
    public Result newArticle(@RequestBody String pageParams) {
        return Result.success(articleService.newArticle(JSONObject.parseObject(pageParams, PageParams.class).getPageSize()));
    }

    @GetMapping("/hot")
    public Result hotArticle(@RequestBody String pageParams) {
        return Result.success(articleService.hotArticle(JSONObject.parseObject(pageParams, PageParams.class).getPageSize()));
    }

    @GetMapping("/listArchives")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }

    @GetMapping("/view/{id}")
    @Cache(name = "article", time = 30 * 60 * 60)
    public Result findArticleById(@PathVariable("id") String articleId) {
        ArticleVo articleVo = articleService.findArticleById(articleId);
        if (articleVo == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return Result.success(articleVo);
        }
    }

    @PostMapping("/publish")
    public Result articlePublish(@RequestBody ArticleParam articleParam) {
        return Result.success(articleService.publish(articleParam));
    }

    @PostMapping("/support/article")
    @Cache(name = "article", time = 30 * 60 * 60)
    public Result support(@RequestBody ArticleParam articleParam) {
        return Result.success(articleService.support(articleParam.getId()));
    }

    @PostMapping("/delete/article")
    public Result deleteArticle(@RequestBody ArticleParam articleParam) {
        return Result.success(articleService.deleteArticle(articleParam.getId()));
    }

    @PostMapping("/delete/comment")
    public Result deleteComment(@RequestBody CommentParam commentParam) {
        articleService.deleteComment(commentParam.getId());
        return Result.success(null);
    }

}
