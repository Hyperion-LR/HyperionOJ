package com.hyperionoj.web.presentation.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/11
 */
@Data
@Builder
public class UpdateSubmitVO {

    private String authorId;

    private String problemId;

    private String status;

}
