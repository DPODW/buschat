package com.dpod.buschat.businfo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUSROUTE_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BusRouteInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCE")
    private Long sequence;

    @Column(name = "BRTID")
    private String brtId;

    @Column(name = "BRTNO")
    private String brtNo;

    @Column(name = "BRTNAME")
    private String brtName;

    @Column(name = "DIRECTION")
    private String direction;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "BRTTYPE")
    private String brtType;

    @Column(name = "CLASS")
    private String busClass;

    @Column(name = "STOPSTID")
    private String stopStId;

    @Column(name = "STOPEDID")
    private String stopEdId;

    @Builder
    public BusRouteInfo(Long sequence, String brtId, String brtNo, String brtName, String direction, String company, String brtType, String busClass, String stopStId, String stopEdId) {
        this.sequence = sequence;
        this.brtId = brtId;
        this.brtNo = brtNo;
        this.brtName = brtName;
        this.direction = direction;
        this.company = company;
        this.brtType = brtType;
        this.busClass = busClass;
        this.stopStId = stopStId;
        this.stopEdId = stopEdId;
    }
}
