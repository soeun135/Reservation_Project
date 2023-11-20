package com.soni.reservation.exception;

import com.soni.reservation.type.ErrorCode;
import lombok.*;

@Getter
public class ReserveException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String errorMessage;

    public ReserveException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
