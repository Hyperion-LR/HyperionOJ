package com.hyperionoj.oss.service.impl;

import com.hyperionoj.oss.dao.mapper.admin.AdminMapper;
import com.hyperionoj.oss.dao.pojo.admin.Admin;
import com.hyperionoj.oss.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hyperionoj.common.constants.Constants.SLAT;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     *
     * @param account  账号
     * @param password 密码
     * @return token
     */
    @Override
    public Admin findAdmin(String account, String password) {
        Admin admin = adminMapper.selectById(account);
        password = DigestUtils.md5Hex(password + SLAT);
        if (StringUtils.compare(admin.getPassword(), password) == 0) {
            return admin;
        }
        return null;
    }
}
