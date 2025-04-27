package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusRouteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public void saveBusRouteInfo() {
        busRouteInfoRepo.saveAll(createBusRouteInfoEntity());
    }

    @Override
    public void updateBusRouteInfo() {
        busRouteInfoRepo.deleteAllInBatch();
        busRouteInfoRepo.resetBusRouteInfoSequence();
        busRouteInfoRepo.saveAll(createBusRouteInfoEntity());
        //데이터가 있으면 AUTO_INCREMENT 를 초기화할 수 없기 때문에 삭제 후 초기화
    }


    @Override
    public List<BusRouteInfo> createBusRouteInfoEntity() {
        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo();
        List<BusRouteInfo> busRouteInfoList = new ArrayList<>();

        busRouteInfoDtoList.forEach(
                busRouteInfoDto -> {
                    BusRouteInfo busRouteInfo = toEntityConvert.busRouteDtoToEntity(busRouteInfoDto);
                    busRouteInfoList.add(busRouteInfo);
                });
        return busRouteInfoList;
    }


    @Override
    public List<BusArrivalInfoDto> plusInfoToBusRouteNm(List<BusArrivalInfoDto> busArrivalInfoDtoList) {
        //API 에서 제공받은 busRouteNm 값은 방면 정보가 없음 -> 방면 정보를 추가해주는 로직
        busArrivalInfoDtoList.forEach(
                busArrivalInfoDto -> {
                    BusRouteInfo busRouteInfosByBrtId = busRouteInfoRepo.findBusRouteInfosByBrtId(busArrivalInfoDto.getBusRouteId());
                    busArrivalInfoDto.setBusRouteNm(busRouteInfosByBrtId.getBrtName());
                    //busArrivalInfoDto 의 유일한 setter 사용 구간
                }
        );
        return busArrivalInfoDtoList;
    }


    @Override
    public int countBusRouteInfo() {
        return busRouteInfoRepo.countAllBy();
    }
}
