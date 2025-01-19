package com.dpod.buschat.businfo.repo.bus;

import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRouteRepo {

    void saveBusStopRoute(String busStopId,String busRouteNo);

}
