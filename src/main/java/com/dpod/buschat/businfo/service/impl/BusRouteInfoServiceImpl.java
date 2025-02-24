package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusRouteInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusRouteInfoServiceImpl implements BusRouteInfoService {

    private final BusInfoApiService busInfoApiService;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToEntityConvert toEntityConvert;

    public BusRouteInfoServiceImpl(BusInfoApiService busInfoApiService, BusRouteInfoRepo busRouteInfoRepo, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.toEntityConvert = toEntityConvert;
    }

    @Override
    public void saveBusRouteInfo(String pageNo, String totalCount) {
        busRouteInfoRepo.saveAll(createBusRouteInfoEntity(pageNo, totalCount));
    }

    @Override
    public void updateBusRouteInfo(String pageNo, String totalCount) {
        busRouteInfoRepo.deleteAllInBatch();
        busRouteInfoRepo.resetBusRouteInfoSequence();
        busRouteInfoRepo.saveAll(createBusRouteInfoEntity(pageNo, totalCount));
        //데이터가 있으면 AUTO_INCREMENT 를 초기화할 수 없기 때문에 삭제 후 초기화
    }


    @Override
    public List<BusRouteInfo> createBusRouteInfoEntity(String pageNo, String totalCount) {
        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo(pageNo, totalCount);
        List<BusRouteInfo> busRouteInfoList = new ArrayList<>();

        busRouteInfoDtoList.forEach(
                busRouteInfoDto -> {
                    BusRouteInfo busRouteInfo = toEntityConvert.busRouteDtoToEntity(busRouteInfoDto);
                    busRouteInfoList.add(busRouteInfo);
                });
        return busRouteInfoList;
    }


    @Override
    public int countBusRouteInfo() {
        return busRouteInfoRepo.countAllBy();
    }
}
