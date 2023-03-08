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

}