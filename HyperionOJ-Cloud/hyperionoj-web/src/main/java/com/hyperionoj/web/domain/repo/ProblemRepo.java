package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.ProblemMapper;
import com.hyperionoj.web.infrastructure.po.ProblemPO;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemRepo extends ServiceImpl<ProblemMapper, ProblemPO> {
    public IPage<ProblemPO> problemList(Page<ProblemPO> page, Integer level, Long id) {
        return baseMapper.problemList(page, level, id);
    }
}
