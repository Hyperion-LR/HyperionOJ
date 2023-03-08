package com.hyperionoj.web.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyperionoj.web.application.api.AdminService;
import com.hyperionoj.web.domain.bo.AdminToken;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.domain.repo.UserRepo;
import com.hyperionoj.web.domain.repo.AdminRepo;
import com.hyperionoj.web.infrastructure.mapper.AdminActionMapper;
import com.hyperionoj.web.infrastructure.po.AdminActionPO;
import com.hyperionoj.web.infrastructure.po.AdminPO;
import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.infrastructure.utils.JWTUtils;
import com.hyperionoj.web.infrastructure.utils.RedisUtils;
import com.hyperionoj.web.presentation.dto.param.ActionPageParams;
import com.hyperionoj.web.presentation.dto.param.LoginAdminParam;
import com.hyperionoj.web.presentation.dto.param.RegisterAdminParam;
import com.hyperionoj.web.presentation.vo.AdminActionVO;
import com.hyperionoj.web.presentation.vo.AdminVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.hyperionoj.web.infrastructure.constants.Constants.SALT;
import static com.hyperionoj.web.infrastructure.constants.Constants.TOKEN;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepo adminRepo;

    @Resource
    private UserRepo userRepo;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AdminActionMapper adminActionMapper;

    /**
     * 管理员查找
     *
     * @param account 账号
     * @return 管理员信息
     */
    @Override
    public AdminPO findAdmin(Long account) {
        return adminRepo.getById(account);
    }

    /**
     * 管理员登陆
     *
     * @param loginAdminParam 登陆参数
     * @return token
     */
    @Override
    public String login(LoginAdminParam loginAdminParam) {
        long id = Long.parseLong(loginAdminParam.getId());
        String password = loginAdminParam.getPassword();
        if (id == 0 || StringUtils.isBlank(password)) {
            return null;
        }
        AdminPO admin = findAdmin(id);
        password = passwordEncrypt(password);
        if (admin != null && StringUtils.compare(admin.getPassword(), password) == 0) {
            AdminToken adminToken = new AdminToken();
            BeanUtils.copyProperties(admin, adminToken);
            String token = JWTUtils.createToken(adminToken.getId(), 24 * 60 * 60);
            adminToken.setToken(TOKEN + token);
            redisUtils.setRedisKV(TOKEN + token, JSON.toJSONString(adminToken), 3600);
            return token;
        }
        return null;
    }

    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     */
    @Override
    public AdminVO register(RegisterAdminParam registerParam) {
        if (findAdmin(Long.parseLong(registerParam.getId())) != null) {
            return null;
        }
        registerParam.setPassword(passwordEncrypt(registerParam.getPassword()));
        AdminPO admin = MapStruct.toPO(registerParam);
        adminRepo.save(admin);
        admin.setPassword(null);
        return MapStruct.toVO(admin);
    }

    /**
     * 更新管理员
     *
     * @param registerParam 注册参数
     * @return 管理员消息更改结果
     */
    @Override
    public AdminVO updateAdmin(RegisterAdminParam registerParam) {
        AdminPO admin = findAdmin(Long.parseLong(registerParam.getId()));
        if (admin == null) {
            return null;
        }
        admin.setName(registerParam.getName());
        admin.setPassword(passwordEncrypt(registerParam.getPassword()));
        adminRepo.updateById(admin);
        admin.setPassword(null);
        return MapStruct.toVO(admin);
    }

    /**
     * 删除管理员
     *
     * @param id 要删除的管理员id
     */
    @Override
    public boolean deleteAdmin(Long id) {
        if (findAdmin(id) == null) {
            return false;
        }
        adminRepo.removeById(id);
        return true;
    }

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    @Override
    public boolean freezeUser(Long id) {
        LambdaUpdateWrapper<UserPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserPO::getId, id).set(UserPO::getStatus, 1);
        return userRepo.update(updateWrapper);
    }

    /**
     * 查询行为记录列表
     *
     * @param pageParams 分页参数
     * @return 列表
     */
    @Override
    public List<AdminActionVO> queryActionList(ActionPageParams pageParams) {
        Page<AdminActionPO> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<AdminActionPO> problemPage = adminActionMapper.listAction(
                page,
                pageParams.getAdminId(),
                pageParams.getAction());
        return problemPage.getRecords().stream().map(MapStruct::toVO).collect(Collectors.toList());
    }

    private String passwordEncrypt(String password){
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
    }

}
