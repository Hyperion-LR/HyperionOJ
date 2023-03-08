package com.hyperionoj.web.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.ProblemTagsService;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.infrastructure.mapper.TagMapper;
import com.hyperionoj.web.infrastructure.po.TagPO;
import com.hyperionoj.web.presentation.vo.TagVO;
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

    public TagVO copy(TagPO tag) {
        return MapStruct.toVO(tag);
    }

    public List<TagVO> copyList(List<TagPO> tagList) {
        List<TagVO> tagVoList = new ArrayList<>();
        for (TagPO tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }


    /**
     * 返回所有标签
     *
     * @return 所有标签
     */
    @Override
    public List<TagVO> findAll() {
        LambdaQueryWrapper<TagPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TagPO::getId, TagPO::getTagName);
        return copyList(tagMapper.selectList(queryWrapper));
    }

    /**
     * 返回所有标签详情
     *
     * @return 所有标签详情
     */
    @Override
    public List<TagVO> findAllDetail() {
        LambdaQueryWrapper<TagPO> queryWrapper = new LambdaQueryWrapper<>();
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
        TagPO tag = tagMapper.selectById(tagId);
        if (tag == null) {
            return null;
        }
        return copy(tag);
    }

}
