package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.web.infrastructure.po.UserJobResourcePO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Repository
public interface UserJobResourceMapper extends BaseMapper<UserJobResourcePO> {

}
