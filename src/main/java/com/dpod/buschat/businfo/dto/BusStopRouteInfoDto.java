package com.dpod.buschat.businfo.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BusStopRouteInfoDto {

    private String brtName;
    //노선 이름 (노선 번호 + 방면 정보)

    private String busStopStId;
    //노선 기점 정류장 ID

    private String busStopEdId;
    //노선 종점 정류장 ID

    private String busStopStName;
    //노선 기점 정류장 이름

    private String busStopEdName;
    //노선 종점 정류장 이름


    @Builder
    public BusStopRouteInfoDto(String brtName, String busStopStId, String busStopEdId, String busStopStName, String busStopEdName) {
        this.brtName = brtName;
        this.busStopStId = busStopStId;
        this.busStopEdId = busStopEdId;
        this.busStopStName = busStopStName;
        this.busStopEdName = busStopEdName;
    }
}
