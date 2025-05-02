package com.dpod.buschat.businfo.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RepCode {
    BUSSTOP_INFO_SAVE(HttpStatus.CREATED,"BUS_STOP-SAVE-SUCCESS-01","요청이 정상적으로 끝났습니다"),
    BUSROUTE_INFO_SAVE(HttpStatus.CREATED,"BUS_ROUTE-SAVE-SUCCESS-01","요청이 정상적으로 끝났습니다"),
    BUSSTOP_ROUTE_INFO_SAVE(HttpStatus.CREATED,"BUS_STOP_ROUTE-SAVE-SUCCESS-01","요청이 정상적으로 끝났습니다"),
    BUSSTOP_TIMETABLE_INFO_SAVE(HttpStatus.CREATED,"BUS_STOP_TIMETABLE-SAVE-SUCCESS-01","요청이 정상적으로 끝났습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    RepCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
