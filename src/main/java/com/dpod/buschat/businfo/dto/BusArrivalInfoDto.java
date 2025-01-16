package com.dpod.buschat.businfo.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
@ToString
public class BusArrivalInfoDto {

    @XmlElement(name = "PREVSTOPCNT")
    private String busPrevStopCnt;
    //남은 정류장 수

    @XmlElement(name = "ARRIVALTIME")
    private int busArrivalTime;
    //도착 시간

    @XmlElement(name = "STOPID")
    private String busStopId;
    //정류장 ID

    @XmlElement(name = "STOPNM")
    private String busStopName;
    //정류장 명

    @XmlElement(name = "PRESENTSTOPNM")
    private String nowBusStopName;
    //현재 정류장 명

    @XmlElement(name = "ROUTENM")
    private String busRouteNm;
    //노선 번호
    
    //추후 확정성을 위해 Builder 객체 생성
    @Builder
    public BusArrivalInfoDto(String busPrevStopCnt, int busArrivalTime, String busStopId, String busStopName, String nowBusStopName, String busRouteNm) {
        this.busPrevStopCnt = busPrevStopCnt;
        this.busArrivalTime = busArrivalTime;
        this.busStopId = busStopId;
        this.busStopName = busStopName;
        this.nowBusStopName = nowBusStopName;
        this.busRouteNm = busRouteNm;
    }
    
}
