package com.dpod.buschat.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class RangeLatLonDto {
    /**
     * 특정 좌표의 범위를 구하기 위해 사용되는 DTO
     * 최소 ~ 최대 좌표를 구하여 범위를 설정한다.
     * **/

    private double minLatitude;

    private double maxLatitude;

    private double minLongitude;

    private double maxLongitude;

    @Builder
    public RangeLatLonDto(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }
}
