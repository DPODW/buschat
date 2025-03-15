package com.dpod.buschat.businfo.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
public class BusTimeTableInfoDto {

    @XmlElement(name = "DIRECTION")
    private String busRouteDirection;
    //방향 정보 (1:정방향 , 2:역방향)

    @XmlElement(name = "TIME")
    private String busStTime;
    //출발 시간

    @XmlElement(name = "CLASS")
    private String busRouteClass;
    //노선 구분 (0:원노선 , 1~8:지원노선)

    @XmlElement(name = "ROUTENAME")
    private String busRouteName;
    //노선 이름 (노선 방면)

    @XmlElement(name = "ROUTENO")
    private String busRouteNo;
    //노선 번호

    @XmlElement(name = "DPTCSEQNO")
    private String timeTableSeq;
    //시간표 순번


}
