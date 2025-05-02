package com.dpod.buschat.businfo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BusInfoExceptionHandler {

    @ExceptionHandler(BusInfoException.class)
    protected ResponseEntity<BusInfoErrorRep> handleCustomException(BusInfoException exception){
        return BusInfoErrorRep.ErrorResponseEntity(exception.getErrorCode());
    }

}
