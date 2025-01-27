package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ToDtoConvert {

    protected BusStopInfoDto busStopEntityToDto(BusStopInfo busStopInfo){
        List<String> busStopRouteIdSplit = List.of(busStopInfo.getBusStopRouteIdList().split("\\|"));

        //TODO: 노선 정보를 BusStopRouteInfoDto 로 변환하는 작업 필요

        return BusStopInfoDto.builder()
                .busStopId(busStopInfo.getBusStopId())
                .busStopName(busStopInfo.getBusStopName())
                .busStopX(busStopInfo.getBusStopX())
                .busStopY(busStopInfo.getBusStopY())
                .busStopMark(busStopInfo.getBusStopMark())
                .busStopRouteIdList(busStopRouteIdSplit)
                .build();
    }


    protected BusRouteInfoDto busRouteInfoEntityToDto(BusRouteInfo busRouteInfo){
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
