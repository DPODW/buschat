package com.dpod.buschat.businfo.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class BusInfoSaveRep {
    private final int httpStatus;
    private final String code;
    private final String message;

    public static ResponseEntity<BusInfoSaveRep> SuccessResponse(RepCode repCode) {
        return ResponseEntity
                .status(repCode.getHttpStatus())
                .body(BusInfoSaveRep.builder()
                        .httpStatus(repCode.getHttpStatus().value())
                        .code(repCode.getCode())
                        .message(repCode.getMessage())
                        .build());
    }

}
