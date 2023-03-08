package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.CategoryMapper;
import com.hyperionoj.web.infrastructure.po.CategoryPO;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepo extends ServiceImpl<CategoryMapper, CategoryPO> {
}
