package com.dpod.buschat.businfo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BUSSTOP_INFO")
public class BusStopInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCE")
    private Long sequence;

    @Column(unique = true,name = "BUSSTOP_ID")
    private String busStopName;

    @Column(name = "BUSSOP_X")
    private String busStopX;

    @Column(name = "BUSSOP_Y")
    private String busStopY;

    @Column(name = "BUSSOP_MARK")
    private String busStopMark;



}
