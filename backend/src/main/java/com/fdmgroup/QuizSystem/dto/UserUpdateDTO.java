package com.fdmgroup.QuizSystem.dto;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

}