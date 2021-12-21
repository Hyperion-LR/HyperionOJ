package com.hyperionoj.page.article.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.page.article.dao.dos.Archives;
import com.hyperionoj.page.article.dao.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 返回文章归档
     *
     * @return 按时间年月分组返回文章数据
     */
    List<Archives> listArchives();

    /**
     * 返回文章归档
     *
     * @param page       分页
     * @param problemId  题目id
     * @param authorId   用户id
     * @param categoryId 分类id
     * @param tagId      标签 id
     * @param year       创建时间(年)
     * @param month      创建时间(月)
     * @return 文章归档分页
     */
    IPage<Article> listArticle(Page<Article> page,
                               @Param("problemId") String problemId,
                               @Param("authorId") String authorId,
                               @Param("categoryId") String categoryId,
                               @Param("tagId") String tagId,
                               @Param("year") String year,
                               @Param("month") String month);

}
