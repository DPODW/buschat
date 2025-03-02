package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusStopInfoService {

    void saveBusStopInfo(String pageNo, String totalCount);

    void updateBusStopInfo(String pageNo, String totalCount);

    List<BusStopInfo> createBusStopInfoEntity(String pageNo,String totalCount);

    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    int countBusStopInfo();

    int countBusStopRoute();

}
