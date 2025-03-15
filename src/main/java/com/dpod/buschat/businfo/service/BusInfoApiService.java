package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoApiService {

    List<BusStopInfoDto> requestBusStopInfo(String pageNo, String totalCount);

    List<BusRouteInfoDto> requestBusRouteInfo(String pageNo, String totalCount);

    List<BusRouteRoadInfoDto> requestBusStopRouteInfo(String routeId, String pageNo, String totalCount);

    List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId,String pageNo, String totalCount);

    List<BusTimeTableInfoDto> requestBusTimeTableInfo(String busRouteName);



}
