package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface BusInfoSaveService {

    void saveBusStopInfo(String pageNo, String totalCount);

    void saveBusRouteInfo(String pageNo,String totalCount);

    void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto);

}
