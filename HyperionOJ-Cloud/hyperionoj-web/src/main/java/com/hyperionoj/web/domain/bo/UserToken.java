package com.hyperionoj.web.domain.bo;

import com.hyperionoj.web.infrastructure.po.UserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hyperion
 * @date 2023/03/08
 *
 * 此类在UserPO的基础上增加token字段用于记录redis中已经登录的用户凭证，
 * 当用户更改密码时通过token字段删除redis中的记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken extends UserPO {

    private String token;

}
