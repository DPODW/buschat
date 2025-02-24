package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface BusStopInfoService {

    void saveBusStopInfo(String pageNo, String totalCount);

    void updateBusStopInfo(String pageNo, String totalCount);

    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    int countBusStopInfo();

    int countBusStopRoute();

}
