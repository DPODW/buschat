package com.dpod.buschat.businfo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUSSTOP_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BusStopInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCE")
    private Long sequence;

    @Column(name = "BUSSTOP_ID")
    private String busStopId;

    @Column(name = "BUSSTOP_NAME")
    private String busStopName;

    @Column(name = "BUSSTOP_X")
    private String busStopX;

    @Column(name = "BUSSTOP_Y")
    private String busStopY;

    @Column(name = "BUSSTOP_MARK")
    private String busStopMark;

    @Builder
    public BusStopInfo(String busStopId,String busStopName, String busStopX, String busStopY, String busStopMark){
     this.busStopId = busStopId;
     this.busStopName = busStopName;
     this.busStopX = busStopX;
     this.busStopY = busStopY;
     this.busStopMark = busStopMark;
    }

}
