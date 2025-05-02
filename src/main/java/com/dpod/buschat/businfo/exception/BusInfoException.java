package com.dpod.buschat.businfo.exception;

import lombok.Getter;

@Getter
public class BusInfoException extends RuntimeException {
    ErrorCode errorCode;

    public BusInfoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
