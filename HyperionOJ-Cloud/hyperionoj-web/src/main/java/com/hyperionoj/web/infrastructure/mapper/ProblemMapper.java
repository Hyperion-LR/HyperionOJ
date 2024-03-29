package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.infrastructure.po.ProblemPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface ProblemMapper extends BaseMapper<ProblemPO> {

    /**
     * 返回题目归档
     *
     * @param page       分页
     * @param level      难度
     * @param categoryId 分类id
     * @return 题目归档分页
     */
    IPage<ProblemPO> problemList(Page<ProblemPO> page, @Param("level") Integer level, @Param("categoryId") Long categoryId);

}
