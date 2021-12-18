package com.hyperionoj.common.feign;

import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Hyperion
 * @date 2021/12/16
 */
@FeignClient("oss")
public interface OSSClients {

    /**
     * 通过id查找用户
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    @GetMapping(value = "/find/user/{id}")
    Result<SysUser> findUserById(@PathVariable("id") String id);

    /**
     * 通过id查找用户
     *
     * @param id 学生学号
     * @return 用户基本信息
     */
    @GetMapping(value = "/find/student/{id}")
    Result<SysUser> findUserByStudentNumber(@PathVariable("id") String id);
}
