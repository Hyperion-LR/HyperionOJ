package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.web.infrastructure.po.PageCategoryPO;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
@Repository
public interface CategoryMapper extends BaseMapper<PageCategoryPO> {
}
