package com.hyperionoj.web.presentation.dto;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2023/3/22
 */
@Data
public class JobWorkingDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long jobId;

    private String type;

    private Integer tmSlot;

    private Integer jmMem;

    private Integer tmMem;

    private Integer parallelism;

    private String flinkId;

    private String jarName;

    private String mainClass;

    private String userSql;

    private String mainArgs;

    private String applicationId;
}
