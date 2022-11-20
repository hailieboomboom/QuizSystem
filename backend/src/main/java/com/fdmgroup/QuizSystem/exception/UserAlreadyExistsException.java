package com.fdmgroup.QuizSystem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Either username or email already exists in the database.
 *
 * @author Jason Liu
 * @version 1.0
 */
@ResponseStatus( value = HttpStatus.CONFLICT, reason = "User with the username or email already exists.")
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
