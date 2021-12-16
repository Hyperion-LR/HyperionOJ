package com.hyperionoj.page.article.service;

import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.page.article.vo.params.CommentParam;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
public interface ArticleCommentService {
    /**
     * 返回文章的评论
     *
     * @param articleId 文章 id
     * @return 该文章下的所有评论
     */
    List<CommentVo> commentByArticleId(Long articleId);

    /**
     * 文章评论
     *
     * @param commentParam 文章和评论信息
     */
    void createComment(CommentParam commentParam);
}
