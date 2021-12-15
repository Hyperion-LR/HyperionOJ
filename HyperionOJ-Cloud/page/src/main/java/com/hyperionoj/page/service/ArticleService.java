package com.hyperionoj.page.service;

import com.hyperionoj.page.dao.dos.Archives;
import com.hyperionoj.page.vo.ArticleVo;
import com.hyperionoj.page.vo.params.ArticleParam;
import com.hyperionoj.page.vo.params.PageParams;

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
    ArticleVo findArticleById(Long articleId);

    /**
     * 发布文章
     *
     * @param articleParam 上传的文章
     * @return 文章 id
     */
    ArticleVo publish(ArticleParam articleParam);

}
