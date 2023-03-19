package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.web.application.api.UserService;
import com.hyperionoj.web.application.api.VerCodeService;
import com.hyperionoj.web.domain.bo.UserToken;
import com.hyperionoj.web.domain.repo.UserJobResourceRepo;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.infrastructure.po.UserJobResourcePO;
import com.hyperionoj.web.infrastructure.utils.JWTUtils;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.presentation.dto.param.LoginParam;
import com.hyperionoj.web.presentation.dto.param.RegisterParam;
import com.hyperionoj.web.presentation.dto.param.UpdatePasswordParam;
import com.hyperionoj.web.presentation.dto.UserDTO;
import com.hyperionoj.web.infrastructure.constants.ErrorCode;

import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepo userRepo;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private VerCodeService verCodeService;

    @Resource
    private UserJobResourceRepo userJobResourceRepo;

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
        UserPO user = findUser(account, password);
        if (user == null) {
            return null;
        }
        String token = ErrorCode.USER_FREEZE.getMsg();
        if (user.getStatus() == 0) {
            UserToken userToken = UserToken.builder().build();
            BeanUtils.copyProperties(user, userToken);
            token = JWTUtils.createToken(userToken.getId(), 24 * 60 * 60);
            userToken.setToken(TOKEN + token);
            redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(userToken), 3600);
        }
        return token;
    }


    /**
     * 注册普通用户
     *
     * @param registerParam 注册参数
     * @return token
     */
    @Override
    public String registerUser(RegisterParam registerParam) {
        if (findUserById(registerParam.getId()) != null) {
            return null;
        }
        UserPO newUser = copyRegisterParamToSysUser(registerParam);
        newUser.setAvatar(DEFAULT_AVATAR);
        userRepo.save(newUser);
        UserToken userToken = UserToken.builder().build();
        BeanUtils.copyProperties(newUser, userToken);
        String token = JWTUtils.createToken(newUser.getId(), 24 * 60 * 60);
        userToken.setToken(TOKEN + token);
        redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(newUser), 3600);
        UserJobResourcePO userJobResourcePO = UserJobResourcePO.builder()
                .userId(newUser.getId())
                .cpuLimit(2000)
                .memLimit(2048)
                .build();
        userJobResourceRepo.save(userJobResourcePO);
        return token;
    }


    /**
     * 更新用户不敏感信息
     *
     * @param userDTO 用户基本信息
     */
    @Override
    public boolean updateUser(UserDTO userDTO) {
        UserPO userPO = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
        userPO.setUsername(userDTO.getUsername());
        userPO.setAvatar(userDTO.getAvatar());
        userPO.setMail(userDTO.getMail());
        return userRepo.updateById(userPO);
    }

    /**
     * 更新用户账号密码
     *
     * @param updateParam 登录信息
     */
    @Override
    public boolean updatePassword(UpdatePasswordParam updateParam) {
        if (verCodeService.checkCode(SUBJECT_UPDATE_PASSWORD, updateParam.getUserMail(), updateParam.getCode())) {
            UserPO userPO = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
            if (StringUtils.compare(updateParam.getUserMail(), userPO.getMail()) == 0) {
                userPO.setPassword(passwordEncrypt(updateParam.getPassword()));
                if(userRepo.updateById(userPO)){
                    UserToken userToken = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserToken.class);
                    redisUtils.delKey(userToken.getToken());
                    return true;
                }
                return false;
            }

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
        UserPO userPO = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
        if (userPO != null) {
            if (userPO.getId().equals(Long.parseLong(destroyParam.getAccount()))) {
                userPO.setStatus(1);
                return userRepo.updateById(userPO);
            }
        }
        return false;
    }

    /**
     * 通过账号密码查找用户(手机号或者邮箱)
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    @Override
    public UserPO findUser(String account, String password) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNumeric(account)) {
            queryWrapper.eq(UserPO::getId, account);
        } else if (StringUtils.indexOf(account, AT) != -1) {
            queryWrapper.eq(UserPO::getMail, account);
        } else {
            return null;
        }
        queryWrapper.last(" limit 1");
        UserPO userPO = userRepo.getOne(queryWrapper);
        if (!ObjectUtils.isEmpty(userPO)) {
            if (password.length() > CODE_LENGTH) {
                password = passwordEncrypt(password);
                if (StringUtils.compare(userPO.getPassword(), password) == 0) {
                    return userPO;
                }
            } else {
                if(verCodeService.checkCode(SUBJECT_LOGIN, userPO.getMail(), password)){
                    return userPO;
                }
            }
        }
        return null;
    }

    /**
     * 通过账号查找是否存在用户
     *
     * @param id 账号
     * @return SysUser
     */
    @Override
    public UserPO findUserById(String id) {
        return userRepo.getById(id);
    }

    private UserPO copyRegisterParamToSysUser(RegisterParam registerParam) {
        UserPO sysUser = new UserPO();
        BeanUtils.copyProperties(registerParam, sysUser);
        sysUser.setId(Long.parseLong(registerParam.getId()));
        sysUser.setPassword(passwordEncrypt(registerParam.getPassword()));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setProblemAcNumber(0);
        sysUser.setProblemSubmitAcNumber(0);
        sysUser.setProblemSubmitNumber(0);
        sysUser.setSalt(SALT);
        sysUser.setStatus(0);
        return sysUser;
    }

    private String passwordEncrypt(String password){
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
    }

}
