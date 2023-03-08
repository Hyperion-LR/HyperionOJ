package com.hyperionoj.web.presentation.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDTO {

    private String id;

    private String name;

    private String password;

    private Integer permissionLevel;

}
