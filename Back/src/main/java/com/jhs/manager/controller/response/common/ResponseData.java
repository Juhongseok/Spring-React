package com.jhs.manager.controller.response.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ResponseData<T> {
    private int statusCode;
    private String message;

    protected ResponseData() {
        this.statusCode = HttpStatus.OK.value();
        this.message = "success";
    }
}
