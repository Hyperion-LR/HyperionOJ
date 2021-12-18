package com.hyperionoj.oss.controller;

import com.hyperionoj.common.pojo.SysUser;
import com.hyperionoj.common.vo.Result;
import com.hyperionoj.oss.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@RequestMapping("/find")
@RestController
public class FindController {

    @Resource
    private SysUserService userService;

    /**
     * 通过id查找用户
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    @GetMapping("user/{id}")
    public Result<SysUser> findUserById(@PathVariable("id") String id) {
        return Result.success(userService.findUserById(id));
    }

    /**
     * 通过id查找用户
     *
     * @param id 学生学号
     * @return 用户基本信息
     */
    @GetMapping(value = "/student/{id}")
    public Result<SysUser> findUserByStudentNumber(@PathVariable("id") String id) {
        return Result.success(userService.findUserByStudentNumber(id));
    }

}
