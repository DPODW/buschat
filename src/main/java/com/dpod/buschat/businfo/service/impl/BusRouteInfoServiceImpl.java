package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusRouteInfoService;

public class BusRouteInfoServiceImpl implements BusRouteInfoService {

    private final BusRouteInfoRepo busRouteInfoRepo;

    public BusRouteInfoServiceImpl(BusRouteInfoRepo busRouteInfoRepo) {
        this.busRouteInfoRepo = busRouteInfoRepo;
    }

    @Override
    public int countBusRouteInfo() {
        return busRouteInfoRepo.countAllBy();
    }
}
