package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusTimeTableInfoXml;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusTimeTableService {

    List<BusTimeTableInfoDto> deleteAnotherDir(List<BusTimeTableInfoDto> busTimeTableInfoDtoList,String busRouteName);

    List<BusTimeTableInfoDto> deleteAnotherClass(List<BusTimeTableInfoDto> busTimeTableInfoDtoList,String busRouteClass);

    List<String> getAvailableTimeTable(String busRouteId);

    void saveTimeTableInfo();

}
