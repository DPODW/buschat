package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRouteRepo {


    void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto);



}
