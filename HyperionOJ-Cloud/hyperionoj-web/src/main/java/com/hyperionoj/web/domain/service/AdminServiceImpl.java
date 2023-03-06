package com.hyperionoj.web.domain.service;

import com.hyperionoj.web.application.api.AdminService;
import com.hyperionoj.web.infrastructure.mapper.AdminMapper;
import com.hyperionoj.web.infrastructure.po.AdminPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 管理员查找
     *
     * @param account 账号
     * @return 管理员信息
     */
    @Override
    public AdminPO findAdmin(Long account) {
        return adminMapper.selectById(account);
    }

    /**
     * 增加管理员
     *
     * @param admin 管理员
     */
    @Override
    public void addAdmin(AdminPO admin) {
        adminMapper.insert(admin);
    }

    /**
     * 更新管理员
     *
     * @param admin 管理员
     */
    @Override
    public void updateAdmin(AdminPO admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 删除管理员
     *
     * @param id 要删除的管理员id
     */
    @Override
    public void deleteAdmin(Long id) {
        adminMapper.deleteById(id);
    }
}
