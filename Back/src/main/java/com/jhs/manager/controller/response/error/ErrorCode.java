package com.jhs.manager.controller.response.error;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),

    MEMBER_NOT_FOUND(400, "Member Not Found"),
    ;

    private int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
