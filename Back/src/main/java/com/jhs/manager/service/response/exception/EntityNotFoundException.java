package com.jhs.manager.service.response.exception;

import com.jhs.manager.controller.response.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
}
