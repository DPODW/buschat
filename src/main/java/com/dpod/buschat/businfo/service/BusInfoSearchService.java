package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoSearchService {

    List<BusStopInfoDto> searchBusStopInfo(String busStopName);

    BusRouteInfoDto searchBusRouteInfo(Long sequence);

    int searchBusStopRouteCount();

}
