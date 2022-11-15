package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.FORBIDDEN, reason = "User is not authorised")
public class UserUnauthorisedError extends RuntimeException{

    public UserUnauthorisedError(){}

    public UserUnauthorisedError(String message){
        super(message);
    }
}
