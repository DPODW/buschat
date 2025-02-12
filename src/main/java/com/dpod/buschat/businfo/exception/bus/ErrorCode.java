package com.dpod.buschat.businfo.exception.bus;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ROUTE_COUNT_MISMATCH(HttpStatus.UNPROCESSABLE_ENTITY, "ROUTE-01", "API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다");

    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// error-code
    private final String message;			// 설명

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
