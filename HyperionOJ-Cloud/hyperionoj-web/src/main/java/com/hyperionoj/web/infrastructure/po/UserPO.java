package com.hyperionoj.web.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class UserPO {

    /**
     * id，唯一标识符
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像，以外链的形式
     */
    private String avatar;

    /**
     * 密码，登录用
     */
    private String password;

    /**
     * 邮箱找密码验证权限
     */
    private String mail;

    /**
     * 用户注册时间
     */
    private Long createTime;

    /**
     * 最后一次登录
     */
    private Long lastLogin;

    /**
     * AC题目数量
     */
    private Integer problemAcNumber;

    /**
     * 题目提交次数
     */
    private Integer problemSubmitNumber;

    /**
     * 提交AC次数
     */
    private Integer problemSubmitAcNumber;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户状态，0：正常 1：已注销
     */
    private Integer status;

}
