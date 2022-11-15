package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.CONFLICT, reason = "Question tage does not match with quiz category")
public class QuestionTagNotMatchQuizCategory extends RuntimeException {

    public QuestionTagNotMatchQuizCategory(){}

    public QuestionTagNotMatchQuizCategory(String message) {
        super(message);
    }
}
