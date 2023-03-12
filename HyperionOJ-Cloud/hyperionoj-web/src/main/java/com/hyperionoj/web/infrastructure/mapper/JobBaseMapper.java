package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@Repository
public interface JobBaseMapper extends BaseMapper<JobBasePO> {
}
