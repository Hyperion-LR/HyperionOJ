package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.ProblemTagMapper;
import com.hyperionoj.web.infrastructure.po.ProblemTagPO;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemTagRepo extends ServiceImpl<ProblemTagMapper, ProblemTagPO> {
}
