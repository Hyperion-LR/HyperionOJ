package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.web.application.api.AdminService;
import com.hyperionoj.web.application.api.OSSService;
import com.hyperionoj.web.application.api.UserService;
import com.hyperionoj.web.domain.dto.*;
import com.hyperionoj.web.infrastructure.utils.JWTUtils;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.web.infrastructure.po.AdminPO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.presentation.vo.ErrorCode;
import com.hyperionoj.web.presentation.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Slf4j
@Service
public class OSSServiceImpl implements OSSService {

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 登录功能
     *
     * @param loginParam 登录参数
     * @return token
     */
    @Override
    public String login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return null;
        }
        UserPO user = userService.findUser(account, password);
        if (user == null) {
            return null;
        }
        String token = ErrorCode.USER_FREEZE.getMsg();
        if (user.getStatus() == 0) {
            token = JWTUtils.createToken(user.getId(), 24 * 60 * 60);
            redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(user), 3600);
        }
        return token;
    }

    /**
     * 管理员登陆
     *
     * @param loginParam 登陆参数
     * @return token
     */
    @Override
    public String adminLogin(LoginParam loginParam) {
        long account = Long.parseLong(loginParam.getAccount());
        String password = loginParam.getPassword();
        if (account == 0 || StringUtils.isBlank(password)) {
            return null;
        }
        AdminPO admin = adminService.findAdmin(account);
        password = DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
        if (admin != null && StringUtils.compare(admin.getPassword(), password) == 0) {
            String token = JWTUtils.createToken(admin.getId(), 24 * 60 * 60);
            redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(admin), 3600);
            return token;
        }
        return null;
    }

    /**
     * 注册普通用户
     *
     * @param registerParam 注册参数
     * @return token
     */
    @Override
    public String registerUser(RegisterParam registerParam) {
        if (userService.findUserById(registerParam.getId()) != null) {
            return null;
        }
        UserPO newUser = copyRegisterParamToSysUser(registerParam);
        userService.insert(newUser);
        String token = JWTUtils.createToken(newUser.getId(), 24 * 60 * 60);
        redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(newUser), 3600);
        return token;
    }

    /**
     * 更新用户不敏感信息
     *
     * @param userVo 用户基本信息
     */
    @Override
    public void updateUser(UserVO userVo) {
        UserPO sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
        sysUser.setUsername(userVo.getUsername());
        sysUser.setMail(userVo.getMail());
        userService.update(sysUser);
    }

    /**
     * 更新用户账号密码
     *
     * @param updateParam 登录信息
     */
    @Override
    public boolean updatePassword(UpdatePasswordParam updateParam) {
        if (StringUtils.compare(updateParam.getCode(), redisUtils.getRedisKV(VER_CODE + updateParam.getUserMail())) == 0) {
            userService.updatePassword(updateParam.getUserMail(), updateParam.getPassword());
            return true;
        }
        return false;
    }

    /**
     * 销毁账户
     * 将账户状态修改为注销
     *
     * @param destroyParam 申请注销的参数
     */
    @Override
    public boolean destroy(LoginParam destroyParam) {
        return userService.destroy(Long.parseLong(destroyParam.getAccount()), destroyParam.getPassword());
    }

    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     */
    @Override
    public AdminPO addAdmin(RegisterAdminParam registerParam) {
        if (adminService.findAdmin(Long.parseLong(registerParam.getId())) != null) {
            return null;
        }
        AdminPO admin = copyRegisterParamToAdmin(registerParam);
        adminService.addAdmin(admin);
        admin.setPassword(null);
        return admin;
    }

    /**
     * 更新管理员
     *
     * @param registerParam 注册参数
     * @return 管理员消息更改结果
     */
    @Override
    public AdminPO updateAdmin(RegisterAdminParam registerParam) {
        AdminPO admin = adminService.findAdmin(Long.parseLong(registerParam.getId()));
        if (admin == null) {
            return null;
        }
        admin.setName(registerParam.getName());
        admin.setPassword(DigestUtils.md5DigestAsHex((registerParam.getPassword() + SALT).getBytes(StandardCharsets.UTF_8)));
        admin.setPermissionLevel(registerParam.getPermissionLevel());
        adminService.updateAdmin(admin);
        admin.setPassword(null);
        return admin;
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     */
    @Override
    public boolean deleteAdmin(Long id) {
        if (adminService.findAdmin(id) == null) {
            return false;
        }
        adminService.deleteAdmin(id);
        return true;
    }

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    @Override
    public void freezeUser(String id) {
        userService.freezeUser(id);
    }

    private UserPO copyRegisterParamToSysUser(RegisterParam registerParam) {
        UserPO sysUser = new UserPO();
        BeanUtils.copyProperties(registerParam, sysUser);
        sysUser.setId(Long.parseLong(registerParam.getId()));
        sysUser.setPassword(DigestUtils.md5DigestAsHex((registerParam.getPassword() + SALT).getBytes(StandardCharsets.UTF_8)));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setProblemAcNumber(0);
        sysUser.setProblemSubmitAcNumber(0);
        sysUser.setProblemSubmitNumber(0);
        sysUser.setSalt(SALT);
        sysUser.setStatus(0);
        return sysUser;
    }

    private AdminPO copyRegisterParamToAdmin(RegisterAdminParam registerParam) {
        AdminPO admin = new AdminPO();
        admin.setId(Long.parseLong(registerParam.getId()));
        admin.setPassword(DigestUtils.md5DigestAsHex((registerParam.getPassword() + SALT).getBytes(StandardCharsets.UTF_8)));
        admin.setName(registerParam.getName());
        admin.setPermissionLevel(registerParam.getPermissionLevel());
        admin.setSalt(SALT);
        return admin;
    }
}
