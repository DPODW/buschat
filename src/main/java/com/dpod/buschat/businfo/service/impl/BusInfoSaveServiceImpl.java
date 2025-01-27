package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusInfoSaveServiceImpl implements BusInfoSaveService {

    private final BusInfoApiService busInfoApiService;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final BusStopRouteRepo busStopRouteRepo;

    private final ToEntityConvert toEntityConvert;


    public BusInfoSaveServiceImpl(BusInfoApiService busInfoApiService, BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo1, BusStopRouteRepo busStopRouteRepo, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo1;
        this.busStopRouteRepo = busStopRouteRepo;
        this.toEntityConvert = toEntityConvert;
    }


    @Override
    public void saveBusStopInfo(String pageNo, String totalCount) {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo(pageNo, totalCount);

        busStopInfoDtoList.forEach(
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busStopInfoRepo.save(busStopInfo);
                    //BULK INSERT 리팩토링 필요
                });
    }

    @Override
    public void saveBusRouteInfo(String pageNo, String totalCount) {
        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo(pageNo, totalCount);

        busRouteInfoDtoList.forEach(
                busRouteInfoDto -> {
                    BusRouteInfo busRouteInfo = toEntityConvert.busRouteDtoToEntity(busRouteInfoDto);
                    busRouteInfoRepo.save(busRouteInfo);
                    //BULK INSERT 리팩토링 필요
                });
    }


    @Override
    public void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto) {
        busStopRouteRepo.saveBusStopRoute(busStopRouteInfoDto);
    }
}
