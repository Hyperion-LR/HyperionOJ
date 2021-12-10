package com.hyperionoj.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.admin.dao.mapper.AdminActionMapper;
import com.hyperionoj.admin.dao.pojo.AdminAction;
import com.hyperionoj.admin.service.AdminService;
import com.hyperionoj.admin.vo.ActionPageParams;
import com.hyperionoj.admin.vo.AdminActionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminActionMapper adminActionMapper;

    /**
     * 查询行为记录列表
     *
     * @param pageParams 分页参数
     * @return 列表
     */
    @Override
    public List<AdminActionVo> queryActionList(ActionPageParams pageParams) {
        Page<AdminAction> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<AdminAction> problemPage = adminActionMapper.listAction(
                page,
                pageParams.getAdminId(),
                pageParams.getAction());
        return copyAdminActionList(problemPage.getRecords());
    }

    private List<AdminActionVo> copyAdminActionList(List<AdminAction> adminActions) {
        List<AdminActionVo> adminActionVos = new ArrayList<>();
        for (AdminAction adminAction : adminActions) {
            adminActionVos.add(copy(adminAction));
        }
        return adminActionVos;
    }

    private AdminActionVo copy(AdminAction adminAction) {
        AdminActionVo adminActionVo = new AdminActionVo();
        adminActionVo.setId(adminAction.getId().toString());
        adminActionVo.setAdminId(adminAction.getAdminId().toString());
        adminActionVo.setAction(adminAction.getAdminAction());
        adminActionVo.setTime(adminAction.getActionTime().toString());
        adminActionVo.setStatus(adminAction.getActionStatus());
        return adminActionVo;
    }
}
