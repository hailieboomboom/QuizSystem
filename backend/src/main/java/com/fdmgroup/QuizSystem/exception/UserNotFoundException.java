package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User is not found in the database.
 *
 * @author Jason Liu
 * @version 1.0
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){}

    public UserNotFoundException(String message) {
        super(message);
    }
}
