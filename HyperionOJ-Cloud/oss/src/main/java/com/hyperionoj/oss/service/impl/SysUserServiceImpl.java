package com.hyperionoj.oss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hyperionoj.common.service.RedisSever;
import com.hyperionoj.common.utils.ThreadLocalUtils;
import com.hyperionoj.oss.dao.mapper.sys.SysUserMapper;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
import com.hyperionoj.oss.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.Constants.*;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisSever redisSever;

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    @Override
    public void freezeUser(String id) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, id).set(SysUser::getStatus, 1);
        sysUserMapper.update(null, updateWrapper);
    }

    /**
     * 通过账号密码查找用户(手机号或者邮箱)
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNumeric(account)) {
            queryWrapper.eq(SysUser::getId, account);
        } else if (StringUtils.indexOf(account, AT) != -1) {
            queryWrapper.eq(SysUser::getMail, account);
        } else {
            return null;
        }
        queryWrapper.last(" limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(sysUser)) {
            if (password.length() > CODE_LENGTH) {
                password = DigestUtils.md5Hex(password + SALT);
                if (StringUtils.compare(sysUser.getPassword(), password) == 0) {
                    return sysUser;
                }
            } else {
                String redisCode = redisSever.getRedisKV(VER_CODE + sysUser.getMail());
                if (StringUtils.compare(redisCode, DigestUtils.md5Hex(password + SALT)) == 0) {
                    return sysUser;
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
    public SysUser findUserById(String id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * 往数据库添加用户
     *
     * @param sysUser 用户信息
     */
    @Override
    public void insert(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    /**
     * 往数据库更新用户基本信息
     *
     * @param sysUser 用户信息
     */
    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 更新用户账号密码
     *
     * @param userMail 账号
     * @param password 新密码
     */
    @Override
    public void updatePassword(String userMail, String password) {
        SysUser sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), SysUser.class);
        if (StringUtils.compare(userMail, sysUser.getMail()) == 0) {
            sysUser.setPassword(DigestUtils.md5Hex(password + SALT));
            sysUserMapper.updateById(sysUser);
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
        SysUser sysUser = JSONObject.parseObject(String.valueOf(ThreadLocalUtils.get()), SysUser.class);
        if (sysUser != null) {
            if (sysUser.getId().equals(account)) {
                sysUser.setStatus(1);
                sysUserMapper.updateById(sysUser);
                return true;
            }
        }
        return false;
    }

    /**
     * 通过id查找用户
     *
     * @param id 学生学号
     * @return 用户基本信息
     */
    @Override
    public SysUser findUserByStudentNumber(String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getStudentNumber, id);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }
}
