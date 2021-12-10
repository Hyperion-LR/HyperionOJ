package com.hyperionoj.admin.service;

import com.hyperionoj.admin.vo.ActionPageParams;
import com.hyperionoj.admin.vo.AdminActionVo;

import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
public interface AdminService {
    /**
     * 查询行为记录列表
     *
     * @param pageParams 分页参数
     * @return 列表
     */
    List<AdminActionVo> queryActionList(ActionPageParams pageParams);
}
