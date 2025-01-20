package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRouteRepo {

    void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto);

}
