package com.hyperionoj.web.presentation.vo;


import lombok.Builder;
import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/15
 */
@Data
@Builder
public class CategoryVO {

    private String id;

    private String categoryName;

    private String description;

}