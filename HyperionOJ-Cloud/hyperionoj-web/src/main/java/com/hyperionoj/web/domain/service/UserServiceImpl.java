package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hyperionoj.web.application.api.UserService;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.infrastructure.utils.ThreadLocalUtils;
import com.hyperionoj.web.infrastructure.mapper.UserMapper;
import com.hyperionoj.web.infrastructure.po.UserPO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.web.infrastructure.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisSever;

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    @Override
    public void freezeUser(String id) {
        LambdaUpdateWrapper<UserPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserPO::getId, id).set(UserPO::getStatus, 1);
        userMapper.update(null, updateWrapper);
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
        UserPO userPO = userMapper.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(userPO)) {
            if (password.length() > CODE_LENGTH) {
                password = DigestUtils.md5Hex(password + SALT);
                if (StringUtils.compare(userPO.getPassword(), password) == 0) {
                    return userPO;
                }
            } else {
                String redisCode = redisSever.getRedisKV(VER_CODE + userPO.getMail());
                if (StringUtils.compare(redisCode, DigestUtils.md5Hex(password + SALT)) == 0) {
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
        return userMapper.selectById(id);
    }

    /**
     * 往数据库添加用户
     *
     * @param sysUser 用户信息
     */
    @Override
    public void insert(UserPO sysUser) {
        userMapper.insert(sysUser);
    }

    /**
     * 往数据库更新用户基本信息
     *
     * @param sysUser 用户信息
     */
    @Override
    public void update(UserPO sysUser) {
        userMapper.updateById(sysUser);
    }

    /**
     * 更新用户账号密码
     *
     * @param userMail 账号
     * @param password 新密码
     */
    @Override
    public void updatePassword(String userMail, String password) {
        UserPO sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
        if (StringUtils.compare(userMail, sysUser.getMail()) == 0) {
            sysUser.setPassword(DigestUtils.md5Hex(password + SALT));
            userMapper.updateById(sysUser);
        }
    }

    /**
     * 注销账号(更新用户状态)
     *
     * @param account  账号id
     * @param password 密码目前没用到
     */
    @Override
    public boolean destroy(Long account, String password) {
        UserPO sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), UserPO.class);
        if (sysUser != null) {
            if (sysUser.getId().equals(account)) {
                sysUser.setStatus(1);
                userMapper.updateById(sysUser);
                return true;
            }
        }
        return false;
    }

}
