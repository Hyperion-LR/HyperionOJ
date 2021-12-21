package com.hyperionoj.page.article.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.params.CommentParam;
import com.hyperionoj.page.article.service.ArticleCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 */
@RestController
@RequestMapping("/article/comments")
public class ArticleCommentsController {

    @Resource
    private ArticleCommentService commentService;

    @GetMapping("/article/{id}")
    public Result commentsByArticle(@PathVariable("id") Long articleId) {
        return Result.success(commentService.commentByArticleId(articleId));
    }

    @PostMapping("/comment")
    public Result createComment(@RequestBody CommentParam commentParam) {
        return Result.success(commentService.createComment(commentParam));
    }

}
