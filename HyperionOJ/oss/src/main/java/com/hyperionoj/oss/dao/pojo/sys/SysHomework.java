package com.hyperionoj.oss.dao.pojo.sys;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Data
public class SysHomework {

    private Long id;

    private Long classId;

    private String name;

    private Long createTime;

    private Long startTime;

    private Long endTime;

    private Long teachId;
}
