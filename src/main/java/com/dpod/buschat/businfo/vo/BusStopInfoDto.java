package com.dpod.buschat.businfo.vo;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Setter
@Getter
@Builder
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
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

    @Builder
    public BusStopInfoDto(String busStopId,String busStopName,String busStopX, String busStopY, String busStopMark){
        this.busStopId = busStopId;
        this.busStopName = busStopName;
        this.busStopX = busStopX;
        this.busStopY = busStopY;
        this.busStopMark = busStopMark;
    }
}
