package com.hyperionoj.web.application.api;


import com.hyperionoj.web.infrastructure.po.AdminPO;
import com.hyperionoj.web.presentation.dto.param.ActionPageParams;
import com.hyperionoj.web.presentation.dto.param.LoginAdminParam;
import com.hyperionoj.web.presentation.dto.param.RegisterAdminParam;
import com.hyperionoj.web.presentation.vo.AdminVO;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
public interface AdminService {

    /**
     * 管理员查找
     *
     * @param account 账号
     * @return 管理员信息
     */
    AdminPO findAdmin(Long account);

    /**
     * 管理员登陆
     *
     * @param loginAdminParam 登陆参数
     * @return token
     */
    String login(LoginAdminParam loginAdminParam);


    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     * @return 返回管理员信息
     */
    AdminVO register(RegisterAdminParam registerParam);

    /**
     * 更新管理员
     *
     * @param registerParam 注册参数
     */
    AdminVO updateAdmin(RegisterAdminParam registerParam);

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return 是否成功删除
     */
    boolean deleteAdmin(Long id);

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    boolean freezeUser(Long id);

    /**
     * 查询行为记录列表
     *
     * @param actionPageParams 分页参数
     * @return 列表
     */
    Object queryActionList(ActionPageParams actionPageParams);
}
