package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoApiService {

    List<BusStopInfoDto> requestBusStopInfo();

    List<BusRouteInfoDto> requestBusRouteInfo();

    List<BusRouteRoadInfoDto> requestBusStopRouteInfo(String routeId);

    List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId);

    List<BusTimeTableInfoDto> requestBusTimeTableInfo(String busRouteName);
}
