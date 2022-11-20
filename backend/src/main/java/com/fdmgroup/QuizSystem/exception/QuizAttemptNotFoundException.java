package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when quiz attempt is not found in database.
 * @author Yutta
 *
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Quiz Attempt not found")
public class QuizAttemptNotFoundException extends RuntimeException {

    public QuizAttemptNotFoundException(){}

    public QuizAttemptNotFoundException(String message) {
        super(message);
    }
}
