package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.web.infrastructure.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hyperion
 */
@Repository
public interface TagMapper extends BaseMapper<TagPO> {
}
