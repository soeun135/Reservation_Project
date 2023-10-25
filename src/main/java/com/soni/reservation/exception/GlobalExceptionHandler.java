package com.soni.reservation.exception;

import com.soni.reservation.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.soni.reservation.type.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ErrorResponse handleUserException(UserException e) {
        log.error("{} is occurred", e.getErrorCode() + " UserException");

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
    @ExceptionHandler(ManagerException.class)
    public ErrorResponse handleManagerException(ManagerException e) {
        log.error("{} is occurred", e.getErrorCode());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(ReserveException.class)
    public ErrorResponse handleReserveException(ReserveException e) {
        log.error("{} is occurred", e.getErrorCode());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(ReviewException.class)
    public ErrorResponse handleReviewException(ReviewException e) {
        log.error("{} is occurred", e.getErrorCode());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(StoreException.class)
    public ErrorResponse handleStoreException(StoreException e) {
        log.error("{} is occurred", e.getErrorCode());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error ("Exception is occurred.", e);

        return new ErrorResponse(INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription());
    }
}
