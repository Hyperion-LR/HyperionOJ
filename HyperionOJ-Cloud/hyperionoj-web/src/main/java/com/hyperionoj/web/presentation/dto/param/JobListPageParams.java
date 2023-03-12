package com.hyperionoj.web.presentation.dto.param;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Data
public class JobListPageParams extends PageParams{

    /**
     * 作业运行状态
     */
    private String status;

}
