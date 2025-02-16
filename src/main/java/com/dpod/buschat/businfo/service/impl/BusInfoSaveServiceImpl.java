package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusInfoSaveServiceImpl implements BusInfoSaveService {

    private final BusInfoApiService busInfoApiService;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final BusStopRouteRepo busStopRouteRepo;

    private final ToEntityConvert toEntityConvert;


    public BusInfoSaveServiceImpl(BusInfoApiService busInfoApiService, BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo, BusStopRouteRepo busStopRouteRepo, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.busStopRouteRepo = busStopRouteRepo;
        this.toEntityConvert = toEntityConvert;
    }


    @Override
    public void saveBusStopInfo(String pageNo, String totalCount) {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo(pageNo, totalCount);

        List<BusStopInfo> busStopInfoList = new ArrayList<>();

        busStopInfoDtoList.forEach(
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busStopInfoList.add(busStopInfo);
                });
        busStopInfoRepo.saveAll(busStopInfoList);
    }


    @Override
    public void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.saveBusStopRoute(busRouteRoadInfoDto);
    }

    @Override
    public void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.updateBusStopRoute(busRouteRoadInfoDto);
    }
}
