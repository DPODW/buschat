package com.dpod.buschat.businfo.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
public class BusRouteInfoDto {

    @XmlElement(name = "BRTID")
    private String brtId;
    //노선 ID

    @XmlElement(name = "BRTNO")
    private String brtNo;
    //노선 번호

    @XmlElement(name = "BRTNAME")
    private String brtName;
    //노선명

    @XmlElement(name = "DIRECTION")
    private String direction;
    //방향

    @XmlElement(name = "COMPANY")
    private String company;
    //회사명

    @XmlElement(name = "BRTTYPE")
    private String brtType;
    //노선타입

    @XmlElement(name = "CLASS")
    private String busClass;
    //노선 구분

    @XmlElement(name = "STOPSTID")
    private String stopStId;
    //기점 정류장 ID

    @XmlElement(name = "STOPEDID")
    private String stopEdId;
    //종점 정류장 ID

    @XmlElement(name = "RNUM")
    private String rNum;
    //API 제공 번호

    @Builder
    public BusRouteInfoDto(String brtId, String brtNo, String brtName, String direction, String company, String brtType, String busClass, String stopStId, String stopEdId) {
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
