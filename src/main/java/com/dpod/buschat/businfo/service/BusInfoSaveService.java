package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface BusInfoSaveService {

    void saveBusStopInfo(String pageNo, String totalCount);

    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

}
