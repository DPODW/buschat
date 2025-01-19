package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusInfoRepository;
import com.dpod.buschat.businfo.service.BusInfoUpdateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusInfoUpdateServiceImpl {

    private final BusInfoRepository busInfoRepository;

    public BusInfoUpdateServiceImpl(BusInfoRepository busInfoRepository) {
        this.busInfoRepository = busInfoRepository;
    }

}
