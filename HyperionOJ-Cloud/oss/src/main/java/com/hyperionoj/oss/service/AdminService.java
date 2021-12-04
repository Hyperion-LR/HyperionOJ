package com.hyperionoj.oss.service;

import com.hyperionoj.oss.dao.pojo.admin.Admin;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
public interface AdminService {

    /**
     * 管理员登录
     *
     * @param account  账号
     * @param password 密码
     * @return token
     */
    Admin findAdmin(String account, String password);
}
