package com.booleanuk.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private List<Integer> game;

}
