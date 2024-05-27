package com.managment.Librarydemo.exception;

import lombok.Data;

@Data

public class CustomerErrorResponse extends RuntimeException {
    private int status;
    private String message;
    private long timestamp;
    private String errorType;

    public CustomerErrorResponse() {
    }

    public CustomerErrorResponse(String message) {
        super(message);
    }

    public CustomerErrorResponse(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerErrorResponse(Throwable cause) {
        super(cause);
    }

}
