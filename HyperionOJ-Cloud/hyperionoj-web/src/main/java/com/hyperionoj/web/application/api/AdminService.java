package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.AdminPO;

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
    AdminPO findAdmin(Long account);

    /**
     * 增加管理员
     *
     * @param admin 管理员
     */
    void addAdmin(AdminPO admin);

    /**
     * 更新管理员
     *
     * @param admin 管理员
     */
    void updateAdmin(AdminPO admin);

    /**
     * 删除管理员
     *
     * @param id 要删除的管理员id
     */
    void deleteAdmin(Long id);

}
