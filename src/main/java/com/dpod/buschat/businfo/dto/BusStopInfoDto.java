package com.dpod.buschat.businfo.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import java.util.List;


@Getter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
@ToString
public class BusStopInfoDto {

    @XmlElement(name = "STOPID")
    private String busStopId;
    //정류장 ID

    @XmlElement(name = "STOPNAME")
    private String busStopName;
    //정류장 이름

    @XmlElement(name = "STOPX")
    private String busStopX;
    //정류장 경도

    @XmlElement(name = "STOPY")
    private String busStopY;
    //정류장 위도

    @XmlElement(name = "STOPREMARK")
    private String busStopMark;
    //정류장 비고 ( 방면 정보 )

    private List<String> busStopRouteIdList;
    //정류장마다 멈추는 노선 아이디 LIST

    @Builder
    public BusStopInfoDto(String busStopId,String busStopName,String busStopX, String busStopY, String busStopMark,List<String> busStopRouteIdList) {
        this.busStopId = busStopId;
        this.busStopName = busStopName;
        this.busStopX = busStopX;
        this.busStopY = busStopY;
        this.busStopMark = busStopMark;
        this.busStopRouteIdList = busStopRouteIdList;
    }
}
