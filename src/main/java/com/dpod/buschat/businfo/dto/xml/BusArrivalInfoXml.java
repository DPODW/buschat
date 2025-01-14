package com.dpod.buschat.businfo.dto.xml;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
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
public class BusArrivalInfoXml {

    @XmlElement(name = "list")
    private BusArrivalInfoXmlList busArrivalInfoXmlList;

    @Getter
    @ToString
    @XmlAccessorType(XmlAccessType.NONE)
    public static class BusArrivalInfoXmlList{

        @XmlElement(name = "row")
        private List<BusArrivalInfoDto> busArrivalInfoDtoList;
    }

}
