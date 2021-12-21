package com.hyperionoj.page.article.service;

import com.hyperionoj.common.vo.page.CommentVo;
import com.hyperionoj.common.vo.params.CommentParam;

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
     * @return 评论详情
     */
    CommentVo createComment(CommentParam commentParam);
}
