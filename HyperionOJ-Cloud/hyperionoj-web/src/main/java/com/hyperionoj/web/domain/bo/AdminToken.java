package com.hyperionoj.web.domain.bo;

import com.hyperionoj.web.infrastructure.po.AdminPO;
import lombok.Data;


/**
 * @author Hyperion
 * @date 2023/03/08
 *
 * 此类在 AdminPO 的基础上增加token字段用于记录redis中已经登录的管理员凭证，
 * 当管理员更改密码时通过token字段删除redis中的记录
 */
@Data
public class AdminToken extends AdminPO {

    private String token;

}
