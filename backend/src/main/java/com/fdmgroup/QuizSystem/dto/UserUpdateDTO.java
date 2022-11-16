package com.fdmgroup.QuizSystem.dto;
import com.fdmgroup.QuizSystem.model.Role;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDTO {

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;

}
