package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.JobWorkingMapper;
import com.hyperionoj.web.infrastructure.po.JobWorkingPO;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@Repository
public class JobWorkingRepo extends ServiceImpl<JobWorkingMapper, JobWorkingPO> {
}
