package com.project.game.common;

import java.util.Objects;

public class CallApiResult<T> {
    private boolean status;
    private String message;
    private T data;
    private Integer statusCode;

    public CallApiResult(boolean status, String message, Integer statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

    public CallApiResult() {
    }

    public CallApiResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
