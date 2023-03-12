package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.JobBaseMapper;
import com.hyperionoj.web.infrastructure.po.JobBasePO;
import com.hyperionoj.web.presentation.dto.param.JobListPageParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@Repository
public class JobBaseRepo extends ServiceImpl<JobBaseMapper, JobBasePO> {

    public List<JobBasePO> list(JobListPageParams pageParams, Long ownerId) {
        LambdaQueryWrapper<JobBasePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobBasePO::getOwnerId, ownerId);
        if (!StringUtils.isEmpty(pageParams.getStatus())) {
            queryWrapper.eq(JobBasePO::getStatus, pageParams.getStatus());
        }
        return baseMapper.selectPage(new Page<>(pageParams.getPage(), pageParams.getPageSize()), queryWrapper).getRecords();
    }
}
