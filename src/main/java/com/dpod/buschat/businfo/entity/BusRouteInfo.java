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
    private String BrtId;

    @Column(name = "BRTNO")
    private String BrtNo;

    @Column(name = "BRTNAME")
    private String BrtName;

    @Column(name = "DIRECTION")
    private String Direction;

    @Column(name = "COMPANY")
    private String Company;

    @Column(name = "BRTTYPE")
    private String BrtType;

    @Column(name = "CLASS")
    private String BusClass;

    @Column(name = "STOPSTID")
    private String StopStId;

    @Column(name = "STOPEDID")
    private String StopEdId;

    @Builder
    public BusRouteInfo(Long sequence, String brtId, String brtNo, String brtName, String direction, String company, String brtType, String busClass, String stopStId, String stopEdId) {
        this.sequence = sequence;
        this.BrtId = brtId;
        this.BrtNo = brtNo;
        this.BrtName = brtName;
        this.Direction = direction;
        this.Company = company;
        this.BrtType = brtType;
        this.BusClass = busClass;
        this.StopStId = stopStId;
        this.StopEdId = stopEdId;
    }
}
