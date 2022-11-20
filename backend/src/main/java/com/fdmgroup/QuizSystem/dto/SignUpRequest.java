package com.fdmgroup.QuizSystem.dto;
import com.fdmgroup.QuizSystem.model.Role;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Sign up request DTO.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String role;
}
