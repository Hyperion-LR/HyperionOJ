package com.hyperionoj.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.ProblemTagsService;
import com.hyperionoj.web.infrastructure.mapper.TagMapper;
import com.hyperionoj.web.infrastructure.po.PageTagPO;
import com.hyperionoj.web.presentation.vo.TagVO;
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

    public TagVO copy(PageTagPO tag) {
        TagVO tagVo = new TagVO();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    public List<TagVO> copyList(List<PageTagPO> tagList) {
        List<TagVO> tagVoList = new ArrayList<>();
        for (PageTagPO tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVO> findTagsByArticleId(Long articleId) {
        List<PageTagPO> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 返回 limit 数量的数量最多的标签
     *
     * @param limit 返回数量
     * @return 返回结果
     */
    @Override
    public List<PageTagPO> hots(Integer limit) {
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
    public List<TagVO> findAll() {
        LambdaQueryWrapper<PageTagPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PageTagPO::getId, PageTagPO::getTagName);
        return copyList(tagMapper.selectList(queryWrapper));
    }

    /**
     * 返回所有标签详情
     *
     * @return 所有标签详情
     */
    @Override
    public List<TagVO> findAllDetail() {
        LambdaQueryWrapper<PageTagPO> queryWrapper = new LambdaQueryWrapper<>();
        return copyList(tagMapper.selectList(queryWrapper));
    }

    /**
     * 返回标签详情
     *
     * @param tagId 标签id
     * @return id标签详情
     */
    @Override
    public TagVO findAllDetailById(Long tagId) {
        PageTagPO tag = tagMapper.selectById(tagId);
        if (tag == null) {
            return null;
        }
        return copy(tag);
    }

}
