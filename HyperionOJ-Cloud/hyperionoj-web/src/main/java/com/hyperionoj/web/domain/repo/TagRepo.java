package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.TagMapper;
import com.hyperionoj.web.infrastructure.po.TagPO;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepo extends ServiceImpl<TagMapper, TagPO> {
}
