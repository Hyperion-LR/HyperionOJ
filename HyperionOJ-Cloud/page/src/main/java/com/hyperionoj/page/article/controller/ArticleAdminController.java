package com.hyperionoj.page.article.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.ArticleParam;
import com.hyperionoj.common.vo.params.CommentParam;
import com.hyperionoj.page.article.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/19
 */
@RestController
@RequestMapping("/article/admin")
public class ArticleAdminController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/delete/article")
    public Result deleteArticle(@RequestBody ArticleParam articleParam) {
        articleService.deleteArticle(articleParam.getId());
        return Result.success(null);
    }

    @PostMapping("/delete/comment")
    public Result deleteComment(@RequestBody CommentParam commentParam) {
        articleService.deleteComment(commentParam.getId());
        return Result.success(null);
    }

}
