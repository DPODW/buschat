package com.dpod.buschat.businfo.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
public class BusInfoErrorRep {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<BusInfoErrorRep>ErrorResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(BusInfoErrorRep.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
