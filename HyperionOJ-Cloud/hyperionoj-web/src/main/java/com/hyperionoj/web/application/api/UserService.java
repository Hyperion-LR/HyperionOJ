package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.UserPO;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface UserService {

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    void freezeUser(String id);

    /**
     * 通过账号密码查找用户
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    UserPO findUser(String account, String password);

    /**
     * 通过账号查找是否存在用户
     *
     * @param id 账号
     * @return SysUser
     */
    UserPO findUserById(String id);

    /**
     * 往数据库添加用户
     *
     * @param sysUser 用户信息
     */
    void insert(UserPO sysUser);

    /**
     * 往数据库更新用户基本信息
     *
     * @param sysUser 用户信息
     */
    void update(UserPO sysUser);

    /**
     * 更新用户账号密码
     *
     * @param userMail 账号
     * @param password 新密码
     */
    void updatePassword(String userMail, String password);

    /**
     * 注销账号(更新用户状态)
     *
     * @param account  账号id
     * @param password 密码目前没用到
     * @return 是否注销成功
     */
    boolean destroy(Long account, String password);

}
