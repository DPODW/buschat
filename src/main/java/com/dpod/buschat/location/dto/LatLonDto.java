package com.dpod.buschat.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatLonDto {

    private String locationCode;
    /// WebSocket 통신 간 위도-경도 검증 메세지 json 파싱 용도로 사용됌 (그외 x)

    private double latitude;
    //위도

    private double longitude;
    //경도

    @Builder
    public LatLonDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
