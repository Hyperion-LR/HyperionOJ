package com.hyperionoj.page.article.controller;

import com.hyperionoj.common.vo.Result;
import com.hyperionoj.page.article.service.ArticleCommentService;
import com.hyperionoj.page.article.vo.params.CommentParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Hyperion
 */
@RestController
@RequestMapping("/article/comments")
public class CommentsController {

    @Resource
    private ArticleCommentService commentService;

    @GetMapping("/article/{id}")
    public Result commentsByArticle(@PathVariable("id") Long articleId) {
        return Result.success(commentService.commentByArticleId(articleId));
    }

    @PostMapping("/comment")
    public Result createComment(@RequestBody CommentParam commentParam) {
        commentService.createComment(commentParam);
        return Result.success(null);
    }

}
