package com.hyperionoj.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.admin.dao.pojo.AdminAction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Repository
public interface AdminActionMapper extends BaseMapper<AdminAction> {
    /**
     * 返回管理员行为列表
     *
     * @param page    分页参数
     * @param adminId 管理员id
     * @param action  管理员行为
     * @return 分页数据
     */
    IPage<AdminAction> listAction(Page<AdminAction> page,
                                  @Param("adminId") Long adminId,
                                  @Param("action") String action);
}
