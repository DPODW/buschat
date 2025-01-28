package com.dpod.buschat.businfo.entity;

import com.querydsl.core.types.EntityPath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUSSTOP_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class BusStopInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCE")
    private Long sequence;

    @Column(name = "BUSSTOP_ID")
    private String busStopId;
    //정류장 ID

    @Column(name = "BUSSTOP_NAME")
    private String busStopName;
    //정류장 이름

    @Column(name = "BUSSTOP_X")
    private String busStopX;
    //정류장 경도

    @Column(name = "BUSSTOP_Y")
    private String busStopY;
    //정류장 위도

    @Column(name = "BUSSTOP_MARK")
    private String busStopMark;
    //정류장 비고 ( 방면 정보 )

    @Column(name = "BUSSTOP_ROUTE_ID")
    private String busStopRouteIdList;
    //정류장마다 멈추는 노선 아이디 ( | 로 구분되어있음 - 이스케이프 처리 필요)

    @Builder
    public BusStopInfo(String busStopId,String busStopName, String busStopX, String busStopY, String busStopMark, String busStopRouteIdList) {
     this.busStopId = busStopId;
     this.busStopName = busStopName;
     this.busStopX = busStopX;
     this.busStopY = busStopY;
     this.busStopMark = busStopMark;
     this.busStopRouteIdList = busStopRouteIdList;
    }

}
