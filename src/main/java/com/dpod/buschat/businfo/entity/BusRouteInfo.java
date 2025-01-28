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
    //컬럼 순서

    @Column(name = "BRTID")
    private String brtId;
    //노선 ID

    @Column(name = "BRTNO")
    private String brtNo;
    //노선 번호

    @Column(name = "BRTNAME")
    private String brtName;
    //노선명

    @Column(name = "DIRECTION")
    private String direction;
    //방향

    @Column(name = "COMPANY")
    private String company;
    //회사명

    @Column(name = "BRTTYPE")
    private String brtType;
    //노선타입

    @Column(name = "CLASS")
    private String busClass;
    //노선 구분

    @Column(name = "STOPSTID")
    private String stopStId;
    //기점 정류장 ID

    @Column(name = "STOPEDID")
    private String stopEdId;
    //종점 정류장 ID

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
