package com.hyperionoj.page.article.service;

import com.hyperionoj.page.article.dao.dos.Archives;
import com.hyperionoj.page.article.vo.ArticleVo;
import com.hyperionoj.page.article.vo.params.ArticleParam;
import com.hyperionoj.page.common.vo.params.PageParams;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
public interface ArticleService {

    /**
     * 分页查询 文章列表
     *
     * @param pageParams 分页参数
     * @return 分页
     */
    List<ArticleVo> listArticle(PageParams pageParams);

    /**
     * 查询最新的文章
     *
     * @param limit 数量
     * @return 最新的文章
     */
    List<ArticleVo> newArticle(int limit);

    /**
     * 查询最火的文章
     *
     * @param limit 数量
     * @return 最火的文章
     */
    List<ArticleVo> hotArticle(Integer limit);

    /**
     * 文章归档
     *
     * @return 所有文章按时间顺序排列
     */
    List<Archives> listArchives();

    /**
     * 通过 articleId 获取文章详情
     *
     * @param articleId 文章id
     * @return 所查找的文章详情
     */
    ArticleVo findArticleById(String articleId);

    /**
     * 发布文章
     *
     * @param articleParam 上传的文章
     * @return 文章 id
     */
    ArticleVo publish(ArticleParam articleParam);

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否删除成功
     */
    Boolean deleteArticle(String id);

    /**
     * 删除评论
     * @param id 评论id
     */
    void deleteComment(String id);
}
