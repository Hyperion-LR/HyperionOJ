package com.hyperionoj.page.problem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.common.vo.page.TagVo;
import com.hyperionoj.page.article.dao.mapper.ArticleTagMapper;
import com.hyperionoj.page.article.dao.pojo.ArticleArticleTag;
import com.hyperionoj.page.common.dao.mapper.TagMapper;
import com.hyperionoj.page.common.dao.pojo.PageTag;
import com.hyperionoj.page.problem.service.ProblemTagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hyperion
 */
@Service
public class ProblemTagsServiceImpl implements ProblemTagsService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    public TagVo copy(PageTag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    public List<TagVo> copyList(List<PageTag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (PageTag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<PageTag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 返回 limit 数量的数量最多的标签
     *
     * @param limit 返回数量
     * @return 返回结果
     */
    @Override
    public List<PageTag> hots(Integer limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (tagIds == null) {
            tagIds = Collections.emptyList();
        }
        return tagMapper.findTagsByTagIds(tagIds);
    }

    /**
     * 返回所有标签
     *
     * @return 所有标签
     */
    @Override
    public List<TagVo> findAll() {
        LambdaQueryWrapper<PageTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PageTag::getId, PageTag::getTagName);
        return copyList(tagMapper.selectList(queryWrapper));
    }

    /**
     * 返回所有标签详情
     *
     * @return 所有标签详情
     */
    @Override
    public List<TagVo> findAllDetail() {
        LambdaQueryWrapper<PageTag> queryWrapper = new LambdaQueryWrapper<>();
        return copyList(tagMapper.selectList(queryWrapper));
    }

    /**
     * 返回标签详情
     *
     * @param tagId 标签id
     * @return id标签详情
     */
    @Override
    public TagVo findAllDetailById(Long tagId) {
        PageTag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            return null;
        }
        return copy(tag);
    }

    /**
     * 像文章添加 id
     *
     * @param articleId 文章 id
     * @param tagId     标签 id
     */
    @Override
    public void addTag(Long articleId, Long tagId) {
        ArticleArticleTag articleTag = new ArticleArticleTag();
        articleTag.setArticleId(articleId);
        articleTag.setTagId(tagId);
        articleTagMapper.insert(articleTag);
    }
}
