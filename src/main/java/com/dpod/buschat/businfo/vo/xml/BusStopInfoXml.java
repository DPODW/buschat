package com.dpod.buschat.businfo.vo.xml;

import com.dpod.buschat.businfo.vo.BusStopInfoDto;
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
