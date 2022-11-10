package com.fdmgroup.QuizSystem.dto;
import com.fdmgroup.QuizSystem.model.Role;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String role;
}
