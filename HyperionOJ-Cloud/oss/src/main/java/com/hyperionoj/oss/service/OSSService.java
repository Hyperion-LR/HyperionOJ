package com.hyperionoj.oss.service;

import com.hyperionoj.oss.dao.pojo.admin.Admin;
import com.hyperionoj.oss.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface OSSService {

    /**
     * 用户登录
     *
     * @param loginParam 登录参数
     * @return token
     */
    String login(LoginParam loginParam);

    /**
     * 管理员登陆
     *
     * @param loginParam 登陆参数
     * @return token
     */
    String adminLogin(LoginParam loginParam);

    /**
     * 注册普通用户
     *
     * @param registerParam 注册参数
     * @return token
     */
    String registerUser(RegisterParam registerParam);

    /**
     * 更新用户不敏感信息
     *
     * @param userVo 用户信息
     */
    void updateUser(@Param("userVo") SysUserVo userVo);

    /**
     * 更新用户账号密码
     *
     * @param updateParam 登录信息
     * @return 是否更新成功
     */
    boolean updatePassword(UpdatePasswordParam updateParam);

    /**
     * 销毁账户
     * 将账户状态修改为注销
     *
     * @param destroyParam 申请注销账号的参数
     * @return 是否销毁成功
     */
    boolean destroy(LoginParam destroyParam);

    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     * @return 返回管理员信息
     */
    Admin addAdmin(RegisterAdminParam registerParam);

    /**
     * 更新管理员
     *
     * @param registerParam 注册参数
     */
    Admin updateAdmin(RegisterAdminParam registerParam);

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return 是否成功删除
     */
    boolean deleteAdmin(Long id);

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    void freezeUser(String id);
}
