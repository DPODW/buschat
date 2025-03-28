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

    @Column(name = "BRT_ID")
    private String brtId;
    //노선 ID

    @Column(name = "BRT_NO")
    private String brtNo;
    //노선 번호

    @Column(name = "BRT_NAME")
    private String brtName;
    //노선명

    @Column(name = "DIRECTION")
    private String direction;
    //방향

    @Column(name = "COMPANY")
    private String company;
    //회사명

    @Column(name = "BRT_TYPE")
    private String brtType;
    //노선타입

    @Column(name = "CLASS")
    private String busClass;
    //노선 구분

    @Column(name = "STOPST_ID")
    private String stopStId;
    //기점 정류장 ID

    @Column(name = "STOPED_ID")
    private String stopEdId;
    //종점 정류장 ID

    @Column(name = "BRT_TIMETABLE")
    private String brtTimeTable;
    //노선별 시간표

    @Builder
    public BusRouteInfo(Long sequence, String brtId, String brtNo, String brtName, String direction, String company, String brtType, String busClass, String stopStId, String stopEdId,String brtTimeTable) {
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
        this.brtTimeTable = brtTimeTable;
    }

    public void updateTimeTable(String brtTimeTable){
        this.brtTimeTable = brtTimeTable;
    }
}
