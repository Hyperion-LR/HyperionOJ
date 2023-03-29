package com.hyperionoj.web.presentation.dto.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hyperion
 * @date 2023/3/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobListPageParams extends PageParams{

    /**
     * 作业运行状态
     */
    private String status;

}
