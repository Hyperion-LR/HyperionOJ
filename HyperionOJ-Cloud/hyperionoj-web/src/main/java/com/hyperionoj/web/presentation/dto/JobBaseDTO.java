package com.hyperionoj.web.presentation.dto;


import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@Data
@Builder
public class JobBaseDTO {

    private String id;

    private String name;

    private String description;

    private String ownerId;

    private String status;

    private String type;

    private String flinkId;

    private String jarName;

    private String mainClass;

    private String mainArgs;

    private String userSql;

    private Integer cpuUsage;

    private Integer memUsage;

}
