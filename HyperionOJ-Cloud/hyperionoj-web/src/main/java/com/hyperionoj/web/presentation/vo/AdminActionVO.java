package com.hyperionoj.web.presentation.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Data
@Builder
public class AdminActionVO {

    private String id;

    private String adminId;

    private String action;

    private String time;

    private Integer status;

}
