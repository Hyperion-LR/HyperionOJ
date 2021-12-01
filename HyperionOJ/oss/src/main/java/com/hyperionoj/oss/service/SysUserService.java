package com.hyperionoj.oss.service;

import com.hyperionoj.oss.dao.pojo.sys.SysUser;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface SysUserService {
    /**
     * 通过账号密码查找用户
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    SysUser findUser(String account, String password);

    /**
     * 通过账号查找是否存在用户
     *
     * @param id 账号
     * @return SysUser
     */
    SysUser findUserById(String id);

    /**
     * 往数据库添加用户
     *
     * @param sysUser 用户信息
     */
    void insert(SysUser sysUser);

    /**
     * 往数据库更新用户基本信息
     *
     * @param sysUser 用户信息
     */
    void update(SysUser sysUser);

    /**
     * 更新用户账号密码
     *
     * @param account  账号
     * @param password 新密码
     */
    void updatePassword(String account, String password);
}
