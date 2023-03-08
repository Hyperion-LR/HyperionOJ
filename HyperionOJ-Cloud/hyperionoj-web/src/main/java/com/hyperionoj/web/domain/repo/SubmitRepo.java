package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.SubmitMapper;
import com.hyperionoj.web.infrastructure.po.SubmitPO;
import org.springframework.stereotype.Repository;

@Repository
public class SubmitRepo extends ServiceImpl<SubmitMapper, SubmitPO> {
}
