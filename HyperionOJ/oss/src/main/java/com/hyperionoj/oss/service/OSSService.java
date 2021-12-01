package com.hyperionoj.oss.service;

import com.hyperionoj.oss.vo.LoginParam;
import com.hyperionoj.oss.vo.RegisterParam;
import com.hyperionoj.oss.vo.SysUserVo;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface OSSService {

    /**
     * 登录功能
     *
     * @param loginParam 登录参数
     * @return token
     */
    String login(LoginParam loginParam);

    /**
     * 注册功能
     *
     * @param registerParam 注册参数
     * @return token
     */
    String register(RegisterParam registerParam);

    /**
     * 更新用户不敏感信息
     *
     * @param userVo 用户信息
     */
    void updateUser(SysUserVo userVo);

    /**
     * 更新用户账号密码
     *
     * @param loginParam 登录信息
     */
    void updatePassword(LoginParam loginParam);
}
