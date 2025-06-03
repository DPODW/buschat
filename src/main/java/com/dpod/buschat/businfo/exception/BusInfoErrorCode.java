package com.dpod.buschat.businfo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusInfoErrorCode {

    BUSSTOP_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "BUS_STOP-ERROR-01", "해당 ID와 일치하는 정류장 정보가 없습니다."),
    BUSSTOP_COUNT_MISMATCH(HttpStatus.CONFLICT, "BUS_STOP-ERROR-02", "API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다릅니다"),

    ROUTE_COUNT_MISMATCH(HttpStatus.CONFLICT, "ROUTE-ERROR-01", "API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    BusInfoErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
