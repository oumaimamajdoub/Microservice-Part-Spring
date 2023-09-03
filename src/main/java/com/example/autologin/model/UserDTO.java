package com.example.autologin.model;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    @JsonIgnore
    private String password;

    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String adress;

    @Size(max = 255)
    private String region;

    @Size(max = 255)
    private String city;

    private Long zip;

    private Set<String> roles;

    private Long image;

}
