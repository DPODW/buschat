package com.dpod.buschat.businfo.dto.xml;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
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
public class BusRouteInfoXml {


    @XmlElement(name ="list")
    private BusRouteInfoXmlList busRouteInfoXmlList;


    @Getter
    @ToString
    @XmlAccessorType(XmlAccessType.NONE)
    public static class BusRouteInfoXmlList{

        @XmlElement(name= "row")
        List<BusRouteInfoDto> busRouteInfoDtoList;
    }

}
