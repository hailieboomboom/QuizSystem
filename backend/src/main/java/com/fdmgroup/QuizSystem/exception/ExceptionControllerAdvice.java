package com.fdmgroup.QuizSystem.exception;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.exception.McqException.McqOptionNotValidException;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.NotEnoughAccessException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * handling some exception
 * and returns Response
 */
@ControllerAdvice
public class ExceptionControllerAdvice {


    /**
     * handles NoDataFoundException
     * @param exception NoDataFoundException
     * @return Api response 404 Error
     */
    @ExceptionHandler(value = NoDataFoundException.class)
    public final ResponseEntity<ApiResponse> handleNoDataFoundException(NoDataFoundException exception){

        return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), HttpStatus.NOT_FOUND);

    }
    /**
     * handles TagNotValidException
     * @param exception TagNotValidException
     * @return Api response 400 Error
     */
    @ExceptionHandler(value = TagNotValidException.class)
    public final ResponseEntity<ApiResponse> handleTagNotValidException(TagNotValidException exception){
        return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles McqOptionNotValidException
     * @param exception McqOptionNotValidException
     * @return Api response with 400 Error
     */
    @ExceptionHandler(value = McqOptionNotValidException.class)
    public final ResponseEntity<ApiResponse> handleMcqOptionNotValidException(McqOptionNotValidException exception){
        return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * handles NotEnoughAccessException
     * @param exception NotEnoughAccessException
     * @return api responsw with Forbidden code
     */
    @ExceptionHandler(value = NotEnoughAccessException.class)
    public final ResponseEntity<ApiResponse> handleNotEnoughAccessException(NotEnoughAccessException exception){
        return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), HttpStatus.FORBIDDEN);
    }

}
