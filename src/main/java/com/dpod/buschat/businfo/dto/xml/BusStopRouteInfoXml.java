package com.dpod.buschat.businfo.dto.xml;


import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@XmlRootElement(name = "tableInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class BusStopRouteInfoXml {

    @XmlElement(name = "list")
    private BusStopRouteInfoXmlList busStopRouteInfoXmlList;

    @Getter
    @ToString
    @XmlAccessorType(XmlAccessType.NONE)
    public static class BusStopRouteInfoXmlList {

        @XmlElement(name = "row")
        private List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList;
    }

}
