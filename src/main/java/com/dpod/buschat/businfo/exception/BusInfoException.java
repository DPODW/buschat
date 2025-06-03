package com.dpod.buschat.businfo.exception;

import lombok.Getter;

@Getter
public class BusInfoException extends RuntimeException {
    BusInfoErrorCode busInfoErrorCode;

    public BusInfoException(BusInfoErrorCode busInfoErrorCode) {
        super(busInfoErrorCode.getMessage());
        this.busInfoErrorCode = busInfoErrorCode;
    }
}
