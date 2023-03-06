package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.infrastructure.po.ProblemCommentPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ProblemCommentMapper extends BaseMapper<ProblemCommentPO> {
    /**
     * 获取评论列表
     *
     * @param page      分页参数
     * @param problemId 题目id
     * @return 评论列表
     */
    IPage<ProblemCommentPO> getCommentList(Page<ProblemCommentPO> page, @Param("problemId") String problemId);

    /**
     * 题目下该评论的点赞数
     *
     * @param commentId 评论id
     * @return 目前得赞数
     */
    Integer support(Long commentId);
}
