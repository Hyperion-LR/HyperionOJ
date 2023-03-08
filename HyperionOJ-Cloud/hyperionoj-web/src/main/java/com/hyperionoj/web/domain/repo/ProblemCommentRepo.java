package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.ProblemCommentMapper;
import com.hyperionoj.web.infrastructure.po.ProblemCommentPO;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemCommentRepo extends ServiceImpl<ProblemCommentMapper, ProblemCommentPO> {
    public IPage<ProblemCommentPO> getCommentList(Page<ProblemCommentPO> page, String problemId) {
        return baseMapper.getCommentList(page, problemId);
    }

    public Integer support(Long commentId) {
        return baseMapper.support(commentId);
    }
}
