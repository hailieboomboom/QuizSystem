package com.fdmgroup.QuizSystem.dto;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDTO {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

}
