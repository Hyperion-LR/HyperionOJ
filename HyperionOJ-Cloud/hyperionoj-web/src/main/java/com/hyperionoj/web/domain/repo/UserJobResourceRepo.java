package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.UserJobResourceMapper;
import com.hyperionoj.web.infrastructure.po.UserJobResourcePO;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2023/3/17
 */
@Repository
public class UserJobResourceRepo extends ServiceImpl<UserJobResourceMapper, UserJobResourcePO> {
}
