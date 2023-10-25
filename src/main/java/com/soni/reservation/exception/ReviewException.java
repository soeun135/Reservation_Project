package com.soni.reservation.exception;

import com.soni.reservation.type.ErrorCode;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ReviewException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
