package com.dpod.buschat.businfo.dto.xml;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
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
public class BusTimeTableInfoXml {

    @XmlElement(name = "list")
    private BusTimeTableInfoList busTimeTableInfoList;


    @Getter
    @ToString
    @XmlAccessorType(XmlAccessType.NONE)
    public static class BusTimeTableInfoList{

        @XmlElement(name = "row")
        private List<BusTimeTableInfoDto> BusTimeTableInfoList;
    }

}
