package com.hyperionoj.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.JWTUtils;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.common.vo.ErrorCode;
import com.hyperionoj.oss.dao.pojo.admin.Admin;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
import com.hyperionoj.oss.service.AdminService;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.service.SysUserService;
import com.hyperionoj.oss.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Slf4j
@Service
public class OSSServiceImpl implements OSSService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private AdminService adminService;

    @Resource
    private RedisSever redisSever;

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
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return null;
        }
        String token = ErrorCode.USER_FREEZE.getMsg();
        if (sysUser.getStatus() == 0) {
            token = JWTUtils.createToken(sysUser.getId(), 24 * 60 * 60);
            redisSever.setRedisKV(TOKEN + token, JSON.toJSONString(sysUser), 3600);
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
        Admin admin = adminService.findAdmin(account);
        password = DigestUtils.md5Hex(password + SALT);
        if (admin != null && StringUtils.compare(admin.getPassword(), password) == 0) {
            String token = JWTUtils.createToken(admin.getId(), 24 * 60 * 60);
            redisSever.setRedisKV(TOKEN + token, JSON.toJSONString(admin), 3600);
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
        if (sysUserService.findUserById(registerParam.getId()) != null) {
            return null;
        }
        SysUser newUser = copyRegisterParamToSysUser(registerParam);
        sysUserService.insert(newUser);
        String token = JWTUtils.createToken(newUser.getId(), 24 * 60 * 60);
        redisSever.setRedisKV(TOKEN + token, JSON.toJSONString(newUser), 3600);
        return token;
    }

    /**
     * 更新用户不敏感信息
     *
     * @param userVo 用户基本信息
     */
    @Override
    public void updateUser(SysUserVo userVo) {
        SysUser sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), SysUser.class);
        sysUser.setUsername(userVo.getUsername());
        sysUser.setAvatar(userVo.getAvatar());
        sysUser.setMail(userVo.getMail());
        sysUserService.update(sysUser);
    }

    /**
     * 更新用户账号密码
     *
     * @param updateParam 登录信息
     */
    @Override
    public boolean updatePassword(UpdatePasswordParam updateParam) {
        if (StringUtils.compare(updateParam.getCode(), redisSever.getRedisKV(VER_CODE + updateParam.getUserMail())) == 0) {
            sysUserService.updatePassword(updateParam.getUserMail(), updateParam.getPassword());
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
        return sysUserService.destroy(Long.parseLong(destroyParam.getAccount()), destroyParam.getPassword());
    }

    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     */
    @Override
    public Admin addAdmin(RegisterAdminParam registerParam) {
        if (adminService.findAdmin(Long.parseLong(registerParam.getId())) != null) {
            return null;
        }
        Admin admin = copyRegisterParamToAdmin(registerParam);
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
    public Admin updateAdmin(RegisterAdminParam registerParam) {
        Admin admin = adminService.findAdmin(Long.parseLong(registerParam.getId()));
        if (admin == null) {
            return null;
        }
        admin.setName(registerParam.getName());
        admin.setPassword(DigestUtils.md5Hex(registerParam.getPassword() + SALT));
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
        sysUserService.freezeUser(id);
    }

    private SysUser copyRegisterParamToSysUser(RegisterParam registerParam) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(registerParam, sysUser);
        sysUser.setId(Long.parseLong(registerParam.getId()));
        sysUser.setPassword(DigestUtils.md5Hex(registerParam.getPassword() + SALT));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setProblemAcNumber(0);
        sysUser.setProblemSubmitAcNumber(0);
        sysUser.setProblemSubmitNumber(0);
        sysUser.setSalt(SALT);
        sysUser.setStatus(0);
        return sysUser;
    }

    private Admin copyRegisterParamToAdmin(RegisterAdminParam registerParam) {
        Admin admin = new Admin();
        admin.setId(Long.parseLong(registerParam.getId()));
        admin.setPassword(DigestUtils.md5Hex(registerParam.getPassword() + SALT));
        admin.setName(registerParam.getName());
        admin.setPermissionLevel(registerParam.getPermissionLevel());
        admin.setSalt(SALT);
        return admin;
    }
}
