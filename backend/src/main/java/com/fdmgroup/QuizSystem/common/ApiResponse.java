package com.fdmgroup.QuizSystem.common;

import java.util.Objects;

/**
 * This class defines Api response object which will be sent as Json object to front end
 * It has two fields success and message,
 * Success is boolean value that indicates whether operation status is successful or not,
 * For example, the request is delete a category, and it succeeded.
 * then success = true, message= "successfully Delete a category"
 */

public class ApiResponse {

    private final boolean success;
    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiResponse)) return false;
        ApiResponse that = (ApiResponse) o;
        return isSuccess() == that.isSuccess() && getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getMessage());
    }

}

