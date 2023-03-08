package com.hyperionoj.web.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String id;

    private String username;

    private String avatar;

    private String mail;

}
