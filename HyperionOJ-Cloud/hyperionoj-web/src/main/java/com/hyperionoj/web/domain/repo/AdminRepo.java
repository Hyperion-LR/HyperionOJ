package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.AdminMapper;
import com.hyperionoj.web.infrastructure.po.AdminPO;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepo extends ServiceImpl<AdminMapper, AdminPO> {
}
