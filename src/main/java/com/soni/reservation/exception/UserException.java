package com.soni.reservation.exception;

import com.soni.reservation.type.ErrorCode;
import lombok.*;

@Getter
public class UserException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String errorMessage;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
