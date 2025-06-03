package com.dpod.buschat.location.exception;

import com.dpod.buschat.businfo.exception.BusInfoErrorRep;
import com.dpod.buschat.businfo.exception.BusInfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LocationExceptionHandler {

    @ExceptionHandler(LocationException.class)
    protected ResponseEntity<LocationErrorRep> handleCustomException(LocationException exception){
        log.error(exception.getMessage());
        return LocationErrorRep.ErrorResponseEntity(exception.getLocationErrorCode());
    }
}
