package com.hyperionoj.oss.service.impl;

import com.hyperionoj.oss.dao.mapper.admin.AdminMapper;
import com.hyperionoj.oss.dao.pojo.admin.Admin;
import com.hyperionoj.oss.service.AdminService;
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
    public Admin findAdmin(Long account) {
        return adminMapper.selectById(account);
    }

    /**
     * 增加管理员
     *
     * @param admin 管理员
     */
    @Override
    public void addAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    /**
     * 更新管理员
     *
     * @param admin 管理员
     */
    @Override
    public void updateAdmin(Admin admin) {
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
