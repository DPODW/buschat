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
@ToString
public class BusRouteInfoDto {

    @XmlElement(name = "BRTID")
    private String BrtId;
    //노선 ID

    @XmlElement(name = "BRTNO")
    private String BrtNo;
    //노선 번호

    @XmlElement(name = "BRTNAME")
    private String BrtName;
    //노선명

    @XmlElement(name = "DIRECTION")
    private String Direction;
    //방향

    @XmlElement(name = "COMPANY")
    private String Company;
    //회사명

    @XmlElement(name = "BRTTYPE")
    private String BrtType;
    //노선타입

    @XmlElement(name = "CLASS")
    private String BusClass;
    //노선 구분

    @XmlElement(name = "STOPSTID")
    private String StopStId;
    //기점 정류장 ID

    @XmlElement(name = "STOPEDID")
    private String StopEdId;
    //종점 정류장 ID

    @Builder
    public BusRouteInfoDto(String brtId, String brtNo, String brtName, String direction, String company, String brtType, String BusClass, String stopStId, String stopEdId) {
        this.BrtId = brtId;
        this.BrtNo = brtNo;
        this.BrtName = brtName;
        this.Direction = direction;
        this.Company = company;
        this.BrtType = brtType;
        this.BusClass = BusClass;
        this.StopStId = stopStId;
        this.StopEdId = stopEdId;
    }



}
