package com.hyperionoj.web.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.infrastructure.po.AdminActionPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface AdminActionMapper extends BaseMapper<AdminActionPO> {
    /**
     * 返回管理员行为列表
     *
     * @param page    分页参数
     * @param adminId 管理员id
     * @param action  管理员行为
     * @return 分页数据
     */
    IPage<AdminActionPO> listAction(Page<AdminActionPO> page,
                                  @Param("adminId") Long adminId,
                                  @Param("action") String action);
}
