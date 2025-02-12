package com.dpod.buschat.businfo.exception.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusInfoExceptionHandler {

    @ExceptionHandler(BusInfoException.class)
    protected ResponseEntity<BusInfoErrorRep> handleCustomException(BusInfoException exception){
        log.error("------API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다------");
        return BusInfoErrorRep.ErrorResponseEntity(exception.getErrorCode());
    }

}
