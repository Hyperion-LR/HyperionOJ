package com.hyperionoj.web.presentation.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobBaseVO {

    private String id;

    private String name;

    private String description;

    private String ownerId;

    private String status;

    private String startTime;

    private String createTime;

    private Integer cpuUsage;

    private Integer memUsage;

    private String flinkUrl;

    private String monitorUrl;

    private String outerUrl;

    private String type;

    private String jarName;

    private String mainClass;

    private String mainArgs;

    private String userSql;

}
