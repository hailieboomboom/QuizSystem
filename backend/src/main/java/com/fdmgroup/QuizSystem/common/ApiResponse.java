package com.fdmgroup.QuizSystem.common;

import java.util.Objects;

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