package com.hyperionoj.oss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.oss.dao.mapper.sys.SysUserMapper;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
import com.hyperionoj.oss.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.OSSConstants.SLAT;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 通过账号密码查找用户
     *
     * @param account  账号
     * @param password 密码
     * @return SysUser
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.last(" limit 1");
        return sysUserMapper.selectOne(queryWrapper);
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
     * @param account  账号
     * @param password 新密码
     */
    @Override
    public void updatePassword(String account, String password) {
        SysUser sysUser = sysUserMapper.selectById(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + SLAT));
        sysUserMapper.updateById(sysUser);
    }
}
