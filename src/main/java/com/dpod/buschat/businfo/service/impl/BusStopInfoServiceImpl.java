package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusStopInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusStopInfoServiceImpl implements BusStopInfoService {

    private final BusInfoApiService busInfoApiService;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusStopRouteRepo busStopRouteRepo;

    private final ToEntityConvert toEntityConvert;


    public BusStopInfoServiceImpl(BusInfoApiService busInfoApiService, BusStopInfoRepo busStopInfoRepo, BusStopRouteRepo busStopRouteRepo, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busStopInfoRepo = busStopInfoRepo;
        this.busStopRouteRepo = busStopRouteRepo;
        this.toEntityConvert = toEntityConvert;
    }


    @Override
    public void saveBusStopInfo(String pageNo, String totalCount) {
        busStopInfoRepo.saveAll(createBusStopInfoEntity(pageNo, totalCount));
    }

    @Override
    public void updateBusStopInfo(String pageNo, String totalCount) {
        busStopInfoRepo.deleteAllInBatch();
        busStopInfoRepo.resetBusStopInfoSequence();
        busStopInfoRepo.saveAll(createBusStopInfoEntity(pageNo, totalCount));
    }

    @Override
    public List<BusStopInfo> createBusStopInfoEntity(String pageNo, String totalCount) {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo(pageNo, totalCount);

        List<BusStopInfo> busStopInfoList = new ArrayList<>();

        busStopInfoDtoList.forEach(
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busStopInfoList.add(busStopInfo);
                });
        return busStopInfoList;
    }


    @Override
    public void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.saveBusStopRoute(busRouteRoadInfoDto);
    }

    @Override
    public void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.updateBusStopRoute(busRouteRoadInfoDto);
    }

    @Override
    public int countBusStopInfo() {
        return busStopInfoRepo.countAllBy();
    }

    @Override
    public int countBusStopRoute() {
        return busStopInfoRepo.countByBusStopRouteIdListIsNotNull();
    }
}
