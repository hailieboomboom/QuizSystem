package com.fdmgroup.QuizSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Role is not defined.
 *
 * @author Jason Liu
 * @version 1.0
 */
@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "Role is not in the scope.")
public class RoleIsOutOfScopeException extends RuntimeException{

}
