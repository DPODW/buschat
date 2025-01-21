package com.dpod.buschat.businfo.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.NONE)
@NoArgsConstructor
@ToString
public class BusStopRouteInfoDto {

    @XmlElement(name = "ROUTEID")
    private String RouteId;
    //노선 id

    @XmlElement(name = "BRSSEQNO")
    private String BrsseqNo;
    //정류장 순번

    @XmlElement(name = "STOPID")
    private String StopId;
    //정류장 id

    @XmlElement(name = "STOPNM")
    private String StopNm;
    //정류장명
}
