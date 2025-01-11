package com.dpod.buschat.businfo.dto.xml;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;

import java.util.List;

@Getter
@XmlRootElement(name = "tableInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class BusStopInfoXml {

    @XmlElement(name = "list")
    private BusStopInfoXmlList busStopInfoXmlList;

    @Getter
    @XmlAccessorType(XmlAccessType.NONE)
    public static class BusStopInfoXmlList{

        @XmlElement(name = "row")
        private List<BusStopInfoDto> busStopInfoDtoList;
    }

}
