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
        //노선 개수 데이터는 500개 정도라, 일일히 SELECT 해서 UPDATE 하는것보다, 전체 삭제 -> 재 저장이 효율적
        busRouteInfoRepo.deleteAllInBatch();
        busRouteInfoRepo.saveAll(createBusRouteInfoEntity(pageNo, totalCount));
    }

    @Override
    public List<BusRouteInfo> createBusRouteInfoEntity(String pageNo, String totalCount) {
        //API 에서 받은 버스 노선 정보를 entity 로 만들어주는 메소드
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
