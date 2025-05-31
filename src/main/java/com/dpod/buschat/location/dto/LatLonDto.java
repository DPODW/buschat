package com.dpod.buschat.location.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatLonDto {

    private double latitude;

    private double longitude;

    @Builder
    public LatLonDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
