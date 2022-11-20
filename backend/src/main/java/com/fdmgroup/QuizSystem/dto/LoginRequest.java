package com.fdmgroup.QuizSystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Login request DTO.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
