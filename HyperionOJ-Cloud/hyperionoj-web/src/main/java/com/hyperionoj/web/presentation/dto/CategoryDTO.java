package com.hyperionoj.web.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    private String id;

    private String categoryName;

    private String description;

}
