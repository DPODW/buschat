package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoService {

    void saveBusStopInfo(List<BusStopInfoDto> busStopInfoDto);

    void saveBusRouteInfo(List<BusRouteInfoDto> busRouteInfoDto);

    void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto);

    List<BusStopInfoDto> searchBusStopInfo(String busStopName);

    BusRouteInfoDto searchBusRouteInfo(Long sequence);

    List<BusStopRouteInfoDto> reqBusStopRouteInfoApi(String secretkey,String routeId);
}
