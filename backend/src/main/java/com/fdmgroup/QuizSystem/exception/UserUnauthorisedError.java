package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User is not authorised to access apis.
 *
 * @author Jason Liu
 * @version 1.0
 */
@ResponseStatus( code = HttpStatus.FORBIDDEN, reason = "User is not authorised")
public class UserUnauthorisedError extends RuntimeException{

    public UserUnauthorisedError(){}

    public UserUnauthorisedError(String message){
        super(message);
    }
}
