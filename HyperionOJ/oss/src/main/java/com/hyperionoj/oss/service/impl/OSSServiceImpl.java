package com.hyperionoj.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.JWTUtils;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
import com.hyperionoj.oss.service.OSSService;
import com.hyperionoj.oss.service.SysUserService;
import com.hyperionoj.oss.vo.LoginParam;
import com.hyperionoj.oss.vo.RegisterParam;
import com.hyperionoj.oss.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.OSSConstants.SLAT;

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
        password = DigestUtils.md5Hex(password + SLAT);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return null;
        }
        String token = JWTUtils.createToken(sysUser.getId(), 24 * 60 * 60);
        redisSever.setRedisKV("TOKEN_" + token, JSON.toJSONString(sysUser), 3600);
        log.info(redisSever.getRedisKV("TOKEN_" + token));
        return token;
    }

    /**
     * 注册功能
     *
     * @param registerParam 注册参数
     * @return token
     */
    @Override
    public String register(RegisterParam registerParam) {
        if (sysUserService.findUserById(registerParam.getId()) != null) {
            return null;
        }
        SysUser newUser = copyRegisterParamToSysUser(registerParam);
        sysUserService.insert(newUser);
        return JWTUtils.createToken(newUser.getId(), 24 * 60 * 60);
    }

    /**
     * 更新用户不敏感信息
     *
     * @param userVo 用户基本信息
     */
    @Override
    public void updateUser(SysUserVo userVo) {
        SysUser sysUser = sysUserService.findUserById(userVo.getId());
        sysUser.setUsername(userVo.getUsername());
        sysUser.setAvatar(userVo.getAvatar());
        sysUser.setMail(userVo.getMail());
        sysUserService.update(sysUser);

    }

    /**
     * 更新用户账号密码
     *
     * @param loginParam 登录信息
     */
    @Override
    public void updatePassword(LoginParam loginParam) {
        sysUserService.updatePassword(loginParam.getAccount(), loginParam.getPassword());
    }

    private SysUser copyRegisterParamToSysUser(RegisterParam registerParam) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(registerParam, sysUser);
        sysUser.setId(Long.parseLong(registerParam.getId()));
        sysUser.setPassword(DigestUtils.md5Hex(registerParam.getPassword() + SLAT));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setProblemAcNumber(0);
        sysUser.setProblemSubmitAcNumber(0);
        sysUser.setProblemSubmitNumber(0);
        sysUser.setSalt(SLAT);
        sysUser.setStatus(0);
        return sysUser;
    }
}
