package com.dpod.buschat.businfo.exception.bus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class BusInfoException extends RuntimeException {
    ErrorCode errorCode;

    public BusInfoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
