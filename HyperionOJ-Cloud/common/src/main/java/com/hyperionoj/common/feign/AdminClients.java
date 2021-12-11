package com.hyperionoj.common.feign;

import com.hyperionoj.common.vo.AdminVo;
import com.hyperionoj.common.vo.RegisterParam;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.common.vo.SysUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@FeignClient("oss")
public interface AdminClients {

    /**
     * 注册管理员
     *
     * @param registerParam 注册参数
     * @return 返回参数
     */
    @PostMapping("/register/admin")
    Result addAdmin(@RequestBody RegisterParam registerParam);

    /**
     * 更新管理员
     *
     * @param registerParam 注册参数
     * @return 返回参数
     */
    @PostMapping("/update/admin")
    Result updateAdmin(@RequestBody RegisterParam registerParam);

    /**
     * 删除管理员管理员
     *
     * @param adminVo 要删除的管理员id
     * @return 返回参数
     */
    @PostMapping("/destroy/admin")
    Result deleteAdmin(AdminVo adminVo);

    /**
     * 冻结普通用户
     *
     * @param userVo 要冻结的用户id
     * @return 返回参数
     */
    @PostMapping("/update/freeze")
    Result freezeUser(SysUserVo userVo);
}
