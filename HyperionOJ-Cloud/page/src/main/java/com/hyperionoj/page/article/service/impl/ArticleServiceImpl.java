package com.hyperionoj.page.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.common.feign.OSSClients;
import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.page.*;
import com.hyperionoj.common.vo.params.ArticleBodyParam;
import com.hyperionoj.common.vo.params.ArticleParam;
import com.hyperionoj.common.vo.params.PageParams;
import com.hyperionoj.page.article.dao.dos.Archives;
import com.hyperionoj.page.article.dao.mapper.ArticleBodyMapper;
import com.hyperionoj.page.article.dao.mapper.ArticleCommentMapper;
import com.hyperionoj.page.article.dao.mapper.ArticleMapper;
import com.hyperionoj.page.article.dao.pojo.Article;
import com.hyperionoj.page.article.dao.pojo.ArticleBody;
import com.hyperionoj.page.article.dao.pojo.ArticleComment;
import com.hyperionoj.page.article.service.ArticleCategoryService;
import com.hyperionoj.page.article.service.ArticleService;
import com.hyperionoj.page.article.service.ArticleTagsService;
import org.apache.commons.codec.digest.DigestUtils;
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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Resource
    private ArticleTagsService articleTagsService;

    @Resource
    private ArticleCategoryService articleCategoryService;

    @Resource
    private OSSClients ossClients;

    @Resource
    private RedisSever redisSever;

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
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCommentCounts(article.getCommentCount());
        articleVo.setWeight(article.getWeight());
        articleVo.setSummary(article.getSummary());
        articleVo.setTitle(article.getTitle());
        articleVo.setViewCounts(article.getViewCount());
        if (article.getCreateTime() != null) {
            articleVo.setCreateDate(dateFormat.format(article.getCreateTime()));
        }
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(articleTagsService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(SysUserVo.userToVo(ossClients.findUserById(authorId.toString()).getData()));
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(articleCategoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    /**
     * ???????????? ????????????
     *
     * @param pageParams ????????????
     * @return ??????
     */
    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getProblemId(),
                pageParams.getAuthorId(),
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return copyList(articleIPage.getRecords(), true, true, false, true);
    }

    /**
     * ?????????????????????
     *
     * @param limit ??????
     * @return ???????????????
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
     * ?????????????????????
     *
     * @param limit ??????
     * @return ???????????????
     */
    @Override
    public List<ArticleVo> hotArticle(Integer limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCount);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return copyList(articles, false, false);
    }

    /**
     * ????????????
     *
     * @return ?????????????????????????????????
     */
    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    /**
     * ?????? id ??????????????????
     *
     * @param articleId ??????id
     */
    @Override
    public ArticleVo findArticleById(String articleId) {
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
     * ????????????
     *
     * @param articleParam ???????????????
     * @return ?????? id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVo publish(ArticleParam articleParam) {
        SysUser author = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        ArticleBodyParam articleBodyParam = articleParam.getBody();
        Article article = new Article();
        article.setAuthorId(author.getId());
        BeanUtils.copyProperties(articleParam, article);
        article.setCommentCount(0);
        article.setViewCount(0);
        article.setWeight(0);
        article.setIsDelete(0);
        article.setIsSolution(articleParam.getIsSolution());
        article.setProblemId(Long.parseLong(articleParam.getProblemId()));
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateTime(System.currentTimeMillis());
        article.setSupport(0);
        articleMapper.insert(article);
        Long articleId = article.getId();
        List<TagVo> tagList = articleParam.getTags();
        for (TagVo tagVo : tagList) {
            articleTagsService.addTag(articleId, Long.parseLong(tagVo.getId()));
        }
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(articleBodyParam, articleBody);
        articleBody.setArticleId(articleId.toString());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        return copy(article, true, true, true, true);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    /**
     * ????????????
     *
     * @param id ??????id
     */
    @Override
    public Boolean deleteArticle(String id) {
        SysUser author = JSONObject.parseObject((String) ThreadLocalUtils.get(), SysUser.class);
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return null;
        }
        if (article.getAuthorId().equals(author.getId()) || author.getPermissionLevel() != null) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, id);
            updateWrapper.set(Article::getIsDelete, 1);
            articleMapper.update(null, updateWrapper);
            String articleVoRedisKey = "article" + ":" +
                    "ArticleController" + ":" +
                    "findArticleById" + ":" +
                    DigestUtils.md5Hex(id);
            redisSever.delKey(articleVoRedisKey);
            return true;
        }
        return false;
    }

    /**
     * ????????????
     *
     * @param id ??????id
     */
    @Override
    public void deleteComment(String id) {
        LambdaUpdateWrapper<ArticleComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ArticleComment::getId, id);
        updateWrapper.set(ArticleComment::getIsDelete, 1);
        articleCommentMapper.update(null, updateWrapper);
        String articleVoRedisKey = "article" + ":" +
                "ArticleController" + ":" +
                "findArticleById" + ":" +
                DigestUtils.md5Hex(id);
        String redisKV = redisSever.getRedisKV(articleVoRedisKey);
        if (redisKV != null) {
            ArticleVo articleVo = JSONObject.parseObject((String) JSONObject.parseObject(redisKV, Result.class).getData(), ArticleVo.class);
            articleVo.setCommentCounts(articleVo.getCommentCounts() - 1);
            redisSever.setRedisKV(articleVoRedisKey, JSONObject.toJSONString(Result.success(articleVo)));
        }
    }

    /**
     * ???????????????
     *
     * @param id ??????id
     * @return ???????????????
     */
    @Override
    public Integer support(String id) {
        return articleMapper.selectById(id).getSupport();
    }

    private ArticleComment voToComment(CommentVo commentVo) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(Long.parseLong(commentVo.getArticleId()));
        articleComment.setContent(commentVo.getContent());
        articleComment.setAuthorId(Long.parseLong(commentVo.getAuthorVo().getId()));
        articleComment.setIsDelete(0);
        articleComment.setLevel(commentVo.getLevel());
        articleComment.setCreateTime(System.currentTimeMillis());
        articleComment.setParentId(Long.getLong(commentVo.getParentId()));
        articleComment.setToUid(Long.getLong(commentVo.getToUser().getId()));
        if (commentVo.getSupportNumber() == null) {
            commentVo.setSupportNumber(0);
        }
        articleComment.setSupport(commentVo.getSupportNumber());
        if (articleComment.getLevel() == null) {
            articleComment.setLevel(0);
        }
        if (articleComment.getToUid() == null) {
            articleComment.setToUid(0L);
        }
        if (articleComment.getParentId() == null) {
            articleComment.setParentId(0L);
        }
        return articleComment;
    }

}
