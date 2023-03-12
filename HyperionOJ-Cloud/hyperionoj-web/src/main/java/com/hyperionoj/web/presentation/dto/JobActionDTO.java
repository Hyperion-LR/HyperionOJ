package com.hyperionoj.web.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobActionDTO {

    private Long jobId;

    @Pattern(regexp = "START|STOP", message = "操作类型不合法")
    private String action;

    private Boolean withSavepoint;

    private Long checkpointId;

    @Pattern(regexp = "CHECKPOINT|CUSTOM_CHECKPOINT|DIRECT", message = "启动类型不合法")
    private String startType;

    private String checkpointPath;

    private Boolean allowNonRestoredState;

}
