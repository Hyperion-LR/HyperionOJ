package com.hyperionoj.web.presentation.vo;

import com.hyperionoj.web.infrastructure.po.UserPO;
import com.hyperionoj.web.presentation.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
@Builder
public class UserVO {

    private String id;

    private String username;

    private String avatar;

    private String mail;

    /**
     * 用户注册时间
     */
    private String createTime;

    /**
     * 最后一次登录
     */
    private String lastLogin;

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

}