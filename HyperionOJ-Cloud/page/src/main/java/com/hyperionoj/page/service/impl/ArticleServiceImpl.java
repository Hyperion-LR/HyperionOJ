package com.hyperionoj.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.page.dao.dos.Archives;
import com.hyperionoj.page.dao.mapper.article.*;
import com.hyperionoj.page.dao.pojo.article.Article;
import com.hyperionoj.page.dao.pojo.article.ArticleBody;
import com.hyperionoj.page.service.ArticleService;
import com.hyperionoj.page.service.CategoryService;
import com.hyperionoj.page.service.TagsService;
import com.hyperionoj.page.vo.ArticleBodyVo;
import com.hyperionoj.page.vo.ArticleVo;
import com.hyperionoj.page.vo.TagVo;
import com.hyperionoj.page.vo.params.ArticleBodyParam;
import com.hyperionoj.page.vo.params.ArticleParam;
import com.hyperionoj.page.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleBodyMapper articleBodyMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private ArticleCommentMapper articleCommentMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private TagsService tagsService;
    @Resource
    private CategoryService categoryService;

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article, isAuthor, isTag, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article, isAuthor, isTag, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isAuthor, boolean isTag, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        if (article.getCreateTime() != null) {
            articleVo.setCreateDate(dateFormat.format(article.getCreateTime()));
        }
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagsService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            //articleVo.setAuthorName(sysUserService.findAuthorByAuthorId(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    /**
     * 分页查询 文章列表
     *
     * @param pageParams 分页参数
     * @return 分页
     */
    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getProblemId(),
                pageParams.getUsername(),
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return copyList(articleIPage.getRecords(), true, true, false, true);
    }

    /**
     * 查询最新的文章
     *
     * @param limit 数量
     * @return 最新的文章
     */
    @Override
    public List<ArticleVo> newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateTime);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles, false, false);
    }

    /**
     * 查询最火的文章
     *
     * @param limit 数量
     * @return 最火的文章
     */
    @Override
    public List<ArticleVo> hotArticle(Integer limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCount);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles, false, false);
    }

    /**
     * 文章归档
     *
     * @return 所有文章按时间顺序排列
     */
    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    /**
     * 通过 id 获取文章详情
     *
     * @param articleId 文章id
     */
    @Override
    public ArticleVo findArticleById(Long articleId) {
        if (articleId == null) {
            return null;
        }
        Article article = this.articleMapper.selectById(articleId);
        if (article == null) {
            return null;
        }
        return copy(article, true, true, true, true);
    }

    /**
     * 发布文章
     *
     * @param articleParam 上传的文章
     * @return 文章 id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVo publish(ArticleParam articleParam) {
        SysUser author = (SysUser) ThreadLocalUtils.get();
        ArticleBodyParam articleBodyParam = articleParam.getBody();
        Article article = new Article();
        article.setAuthorId(author.getId());
        BeanUtils.copyProperties(articleParam, article);
        article.setCommentCount(0);
        article.setViewCount(0);
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateTime(System.currentTimeMillis());
        articleMapper.insert(article);
        Long articleId = article.getId();
        List<TagVo> tagList = articleParam.getTags();
        for (TagVo tagVo : tagList) {
            tagsService.addTag(articleId, Long.parseLong(tagVo.getId()));
        }
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(articleBodyParam, articleBody);
        articleBody.setArticleId(articleId.toString());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        return copy(article, false, false, false, false);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
