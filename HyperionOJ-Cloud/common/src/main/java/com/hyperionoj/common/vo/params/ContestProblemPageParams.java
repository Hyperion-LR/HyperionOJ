package com.hyperionoj.common.vo.params;

import lombok.Data;

@Data
public class ContestProblemPageParams {
    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 比赛id
     */
    private String contestsId;

}
