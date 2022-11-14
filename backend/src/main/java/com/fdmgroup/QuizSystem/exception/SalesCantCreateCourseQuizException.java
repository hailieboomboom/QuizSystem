package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.UNAUTHORIZED, reason = "Sales is not authorised to create course quizzes")
public class SalesCantCreateCourseQuizException extends RuntimeException{

    public SalesCantCreateCourseQuizException(){}

    public SalesCantCreateCourseQuizException(String message){
        super(message);
    }
}
