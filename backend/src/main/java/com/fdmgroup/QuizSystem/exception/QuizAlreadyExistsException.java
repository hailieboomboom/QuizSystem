package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when quiz already exists in database.
 * @author sm
 *
 */
@ResponseStatus( code = HttpStatus.CONFLICT, reason = "Quiz already exists")
public class QuizAlreadyExistsException extends RuntimeException {

    public QuizAlreadyExistsException(){}

    public QuizAlreadyExistsException(String message) {
        super(message);
    }
}
