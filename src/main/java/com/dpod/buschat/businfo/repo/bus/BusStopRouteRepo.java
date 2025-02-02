package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopRouteRepo {


    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);

    List<BusStopRouteInfoDto> searchBusRouteDetail(List<String> busStopRouteIdSplit);


}
