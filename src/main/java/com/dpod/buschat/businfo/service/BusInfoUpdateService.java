package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusInfoUpdateService {

    BusStopInfo busStopRouteUpdate(String busStopId, String busRouteId);
}
