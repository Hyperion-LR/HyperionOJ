package com.hyperionoj.web.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {

    private String id;

    private String tagName;

}
