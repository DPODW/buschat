package com.dpod.buschat.businfo.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BUSSTOP_INFO")
@Setter
@ToString
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

}
