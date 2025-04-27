package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusStopInfoService {

    void saveBusStopInfo();

    void updateBusStopInfo();

    void saveBusStopRouteInfo();

    List<BusStopInfo> createBusStopInfoEntity();

    List<BusArrivalInfoDto> createBusAllArrivalList(List<BusArrivalInfoDto> busArrivalInfoDtoList,List<BusStopRouteInfoDto> busStopRouteInfoList);

    List<BusArrivalInfoDto> createInactiveBusList(List<BusStopRouteInfoDto> busStopRouteInfoList);

    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    int countBusStopInfo();

    int countBusStopRoute();

}
