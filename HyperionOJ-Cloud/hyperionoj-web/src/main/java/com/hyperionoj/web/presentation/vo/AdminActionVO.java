package com.hyperionoj.web.presentation.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
public class AdminActionVO {

    private String id;

    private String adminId;

    private String action;

    private String time;

    private Integer status;

}
