package com.dpod.buschat.location.exception;

import com.dpod.buschat.businfo.exception.BusInfoErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
public class LocationErrorRep {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<LocationErrorRep>ErrorResponseEntity(LocationErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(LocationErrorRep.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
