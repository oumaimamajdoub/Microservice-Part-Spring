package com.example.autologin.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @Size(max = 255)
    private String role;

}
