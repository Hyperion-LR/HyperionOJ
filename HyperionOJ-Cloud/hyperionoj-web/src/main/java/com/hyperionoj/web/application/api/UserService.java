package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.presentation.dto.param.LoginParam;
import com.hyperionoj.web.presentation.dto.param.RegisterParam;
import com.hyperionoj.web.presentation.dto.param.UpdatePasswordParam;
import com.hyperionoj.web.presentation.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param loginParam 登录参数
     * @return token
     */
    String login(LoginParam loginParam);

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
     * @param userDTO 用户信息
     */
    boolean updateUser(@Param("userVo") UserDTO userDTO);

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

}
