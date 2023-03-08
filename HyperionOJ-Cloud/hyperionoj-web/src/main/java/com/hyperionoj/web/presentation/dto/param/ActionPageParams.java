package com.hyperionoj.web.presentation.dto.param;

import lombok.Data;

/**
 * 管理员行为分页查询参数
 *
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class ActionPageParams {

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 管理员id
     */
    private Long adminId;

    /**
     * 管理员行为
     */
    private String action;

}
