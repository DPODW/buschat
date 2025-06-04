package com.dpod.buschat.location.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LocationErrorCode {
    BUSSTOP_NOT_FOUND_50M_RANGE(HttpStatus.NOT_FOUND,"LOCATION-ERROR-02","사용자 위치 50M 반경에 정류장이 없습니다."),
    BUSSTOP_NOT_FOUND_500M_RANGE(HttpStatus.NOT_FOUND,"LOCATION-ERROR-01","사용자 위치 500M 반경에 정류장이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    LocationErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
