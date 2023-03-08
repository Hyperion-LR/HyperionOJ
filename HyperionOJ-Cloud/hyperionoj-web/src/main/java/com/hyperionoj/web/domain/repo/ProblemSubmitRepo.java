package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.ProblemSubmitMapper;
import com.hyperionoj.web.infrastructure.po.ProblemSubmitPO;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemSubmitRepo extends ServiceImpl<ProblemSubmitMapper, ProblemSubmitPO> {
    public IPage<ProblemSubmitPO> getSubmitList(Page<ProblemSubmitPO> page, String problemId, String codeLang, String username, String verdict) {
        return baseMapper.getSubmitList(page, problemId, codeLang, username, verdict);
    }
}
