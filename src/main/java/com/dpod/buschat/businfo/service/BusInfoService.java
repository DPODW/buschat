package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoService {

    void saveBusStopInfo(List<BusStopInfoDto> busStopInfoDto);

    void saveBusRouteInfo(List<BusRouteInfoDto> busRouteInfoDto);

    List<BusStopInfoDto> searchBusStopInfo(String busStopName);


}
