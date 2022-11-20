package com.fdmgroup.QuizSystem.dto;

import lombok.Data;

/**
 * User output DTO.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class UserOutputDTO {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String role;
}
