package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.BusStopRouteRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ToDtoConvert {

    private final BusStopRouteRepo busStopRouteRepo;

    public ToDtoConvert(BusStopRouteRepo busStopRouteRepo) {
        this.busStopRouteRepo = busStopRouteRepo;
    }

    public BusStopInfoDto busStopEntityToDto(BusStopInfo busStopInfo){
        List<String> busStopRouteIdSplit = List.of(busStopInfo.getBusStopRouteIdList().split(","));

        List<BusStopRouteInfoDto> busStopRouteInfoDtoList = busStopRouteRepo.searchBusRouteDetail(busStopRouteIdSplit);

        return BusStopInfoDto.builder()
                .busStopId(busStopInfo.getBusStopId())
                .busStopName(busStopInfo.getBusStopName())
                .busStopLon(busStopInfo.getBusStopLon())
                .busStopLat(busStopInfo.getBusStopLat())
                .busStopMark(busStopInfo.getBusStopMark())
                .busStopRouteInfoList(busStopRouteInfoDtoList)
                .build();
    }


    public BusRouteInfoDto busRouteInfoEntityToDto(BusRouteInfo busRouteInfo){
        return BusRouteInfoDto.builder()
                .brtId(busRouteInfo.getBrtId())
                .brtNo(busRouteInfo.getBrtNo())
                .brtName(busRouteInfo.getBrtName())
                .direction(busRouteInfo.getDirection())
                .company(busRouteInfo.getCompany())
                .brtType(busRouteInfo.getBrtType())
                .busClass(busRouteInfo.getBusClass())
                .stopStId(busRouteInfo.getStopStId())
                .stopEdId(busRouteInfo.getStopEdId())
                .build();
    }
}
