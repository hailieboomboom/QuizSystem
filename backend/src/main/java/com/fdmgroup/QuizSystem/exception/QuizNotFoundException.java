package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when quiz is not found in database.
 * @author sm
 *
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Quiz not found")
public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(){}

    public QuizNotFoundException(String message) {
        super(message);
    }
}
