package com.dpod.buschat.businfo.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Setter
@Getter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
public class BusArrivalInfoDto {

    @XmlElement(name = "PREVSTOPCNT")
    private String busPrevStopCnt;
    //남은 정류장 수

    @XmlElement(name = "ARRIVALTIME")
    private int busArrivalTime;
    //도착 시간

    @XmlElement(name = "ROUTEID")
    private String busRouteId;
    //노선 ID

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

    private String busStartTime;
    //버스 출발 시간 (현재 운행중이 아닌 노선 한정 제공)

    private String busStopStName;
    //버스 출발 정류장 (현재 운행중이 아닌 노선 한정 제공)

    @Builder
    public BusArrivalInfoDto(String busPrevStopCnt, int busArrivalTime,String busRouteId, String busStopId, String busStopName, String nowBusStopName, String busRouteNm,String busStartTime,String busStopStName) {
        this.busPrevStopCnt = busPrevStopCnt;
        this.busArrivalTime = busArrivalTime;
        this.busRouteId = busRouteId;
        this.busStopId = busStopId;
        this.busStopName = busStopName;
        this.nowBusStopName = nowBusStopName;
        this.busRouteNm = busRouteNm;
        this.busStartTime = busStartTime;
        this.busStopStName = busStopStName;
    }
    
}
