package com.dpod.buschat.businfo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusInfoExceptionHandler {

    @ExceptionHandler(BusInfoException.class)
    protected ResponseEntity<BusInfoErrorRep> handleCustomException(BusInfoException exception){
//        log.error(exception.getMessage());
        return BusInfoErrorRep.ErrorResponseEntity(exception.getBusInfoErrorCode());
    }

}
