package com.hyperionoj.oss.service;

import com.hyperionoj.oss.dao.pojo.admin.Admin;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
public interface AdminService {

    /**
     * 管理员查找
     *
     * @param account 账号
     * @return 管理员信息
     */
    Admin findAdmin(Long account);

    /**
     * 增加管理员
     *
     * @param admin 管理员
     */
    void addAdmin(Admin admin);

    /**
     * 更新管理员
     *
     * @param admin 管理员
     */
    void updateAdmin(Admin admin);

    /**
     * 删除管理员
     *
     * @param id 要删除的管理员id
     */
    void deleteAdmin(Long id);

}
