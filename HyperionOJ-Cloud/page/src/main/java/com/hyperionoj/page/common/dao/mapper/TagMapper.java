package com.hyperionoj.page.common.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.page.common.dao.pojo.PageTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hyperion
 */
@Repository
public interface TagMapper extends BaseMapper<PageTag> {
    /**
     * 通过 articleId 找标签
     *
     * @param articleId 文章id
     * @return Tag链表
     */
    List<PageTag> findTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 返回 limit 数量的数量最多的标签
     *
     * @param limit 返回数量
     * @return 返回结果
     */
    List<Long> findHotsTagIds(@Param("limit") int limit);

    /**
     * 通过tagIds返回tag
     *
     * @param tagIds tagId 列表
     * @return 返回 tag 列表
     */
    List<PageTag> findTagsByTagIds(@Param("tagIds") List<Long> tagIds);
}
