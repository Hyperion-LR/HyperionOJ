package com.hyperionoj.page.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.CommentVo;
import com.hyperionoj.page.article.dao.mapper.ArticleCommentMapper;
import com.hyperionoj.page.article.dao.mapper.ArticleMapper;
import com.hyperionoj.page.article.dao.pojo.Article;
import com.hyperionoj.page.article.dao.pojo.ArticleComment;
import com.hyperionoj.page.article.service.ArticleCommentService;
import com.hyperionoj.page.article.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Hyperion
 */
@Service
public class CommentsServiceImpl implements ArticleCommentService {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ArticleCommentMapper commentMapper;
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 返回文章的评论
     *
     * @param articleId 文章 id
     * @return 该文章下的所有评论
     */
    @Override
    public List<CommentVo> commentByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleComment::getArticleId, articleId);
        queryWrapper.eq(ArticleComment::getLevel, 1);
        queryWrapper.orderByDesc(ArticleComment::getCreateTime);
        List<ArticleComment> comments = commentMapper.selectList(queryWrapper);
        if (comments == null) {
            comments = Collections.emptyList();
        }
        return copyList(comments);
    }

    /**
     * 文章评论
     *
     * @param commentParam 文章和评论信息
     */
    @Override
    public void createComment(CommentParam commentParam) {
        SysUser sysUser = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        ArticleComment comment = new ArticleComment();
        comment.setCreateTime(System.currentTimeMillis());
        comment.setArticleId(Long.parseLong(commentParam.getArticleId()));
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
        Long parent = 0L;
        if (commentParam.getParent() != null) {
            parent = Long.valueOf(commentParam.getParent());
            comment.setLevel(2);
        } else {
            comment.setLevel(1);
        }
        comment.setParentId(parent);
        Long toUserId = Long.valueOf(commentParam.getToUserId());
        if (toUserId == 0) {
            toUserId = commentMapper.selectById(parent).getAuthorId();
        }
        comment.setToUid(toUserId == null ? 0 : toUserId);
        comment.setIsDelete(0);
        commentMapper.insert(comment);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, commentParam.getArticleId());
        Article article = articleMapper.selectOne(queryWrapper);
        article.setCommentCount(article.getCommentCount() + 1);
        articleMapper.updateById(article);
    }

    List<CommentVo> copyList(List<ArticleComment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (ArticleComment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    CommentVo copy(ArticleComment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setCreateDate(dateFormat.format(new Date(comment.getCreateTime())));
        Long authorId = comment.getAuthorId();
        // SysUserVo authorUserVo = sysUserService.findUserVoById(authorId);
        // commentVo.setAuthorVo(authorUserVo);
        Integer level = comment.getLevel();
        if (1 == level) {
            LambdaQueryWrapper<ArticleComment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleComment::getParentId, comment.getId());
            queryWrapper.eq(ArticleComment::getLevel, 2);
            List<ArticleComment> commentList = commentMapper.selectList(queryWrapper);
            // commentVo.setChildrens(copyList(commentList));
        }
        if (level > 1) {
            Long toUid = comment.getToUid();
            // SysUserVo toUser = sysUserService.findUserVoById(toUid);
            // commentVo.setToUser(toUser);
        }
        return commentVo;
    }

}
