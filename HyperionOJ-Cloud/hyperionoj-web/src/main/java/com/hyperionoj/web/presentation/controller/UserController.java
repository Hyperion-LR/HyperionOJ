package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.application.api.UserService;
import com.hyperionoj.web.application.api.VerCodeService;
import com.hyperionoj.web.domain.convert.MapStruct;
import com.hyperionoj.web.presentation.dto.*;
import com.hyperionoj.web.presentation.dto.param.LoginParam;
import com.hyperionoj.web.presentation.dto.param.MailCodeParam;
import com.hyperionoj.web.presentation.dto.param.RegisterParam;
import com.hyperionoj.web.presentation.dto.param.UpdatePasswordParam;
import com.hyperionoj.web.infrastructure.constants.ErrorCode;
import com.hyperionoj.web.presentation.vo.Result;
import com.hyperionoj.web.presentation.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.hyperionoj.web.infrastructure.constants.Constants.SUBJECT_REGISTER;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private VerCodeService verCodeService;

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Result userRegisterById(@RequestBody RegisterParam registerParam) {
        if (!verCodeService.checkCode(SUBJECT_REGISTER, registerParam.getMail(), registerParam.getVerCode())) {
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        String token = userService.registerUser(registerParam);
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(token);
    }


    @PostMapping("/login")
    public Result userLoginById(@RequestBody LoginParam loginParam) {
        String token = userService.login(loginParam);
        if (StringUtils.isBlank(token)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.compare(token, ErrorCode.USER_FREEZE.getMsg()) == 0) {
            return Result.fail(ErrorCode.USER_FREEZE);
        }
        return Result.success(token);
    }

    /**
     * 通过id查找用户
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    @GetMapping("find/{id}")
    public Result<UserVO> findUserById(@PathVariable("id") String id) {
        return Result.success(MapStruct.toVO(userService.findUserById(id)));
    }

    /**
     * 更新用户数据
     *
     * @param userDTO 用户信息
     * @return
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return Result.success(null);
    }

    /**
     * 更新用户账号密码
     *
     * @param updateParam 相关参数
     * @return
     */
    @PostMapping("/update/password")
    public Result updatePassword(@RequestBody UpdatePasswordParam updateParam) {
        userService.updatePassword(updateParam);
        return Result.success(null);
    }

    /**
     * 通过邮件获取账号密码
     *
     * @param mailCodeParam 邮件地址扽信息
     * @return
     */
    @PostMapping("/mail/code")
    public Result getCode(@RequestBody MailCodeParam mailCodeParam) {
        try{
            verCodeService.getCode(mailCodeParam.getMail(), mailCodeParam.getSubject());
            return Result.success(true);
        }catch (Exception e){
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        }

    }

}
