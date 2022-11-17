package com.fdmgroup.QuizSystem.dto;

import lombok.Data;

@Data
public class UserOutputDTO {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String role;
}
