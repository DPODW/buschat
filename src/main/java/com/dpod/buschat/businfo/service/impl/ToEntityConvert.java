package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.stereotype.Component;

@Component
public class ToEntityConvert {

    protected BusRouteInfo busRouteDtoToEntity(BusRouteInfoDto busRouteInfoDto){
        return BusRouteInfo.builder()
                .brtId(busRouteInfoDto.getBrtId())
                .brtNo(busRouteInfoDto.getBrtNo())
                .brtName(busRouteInfoDto.getBrtName())
                .direction(busRouteInfoDto.getDirection())
                .company(busRouteInfoDto.getCompany())
                .brtType(busRouteInfoDto.getBrtType())
                .busClass(busRouteInfoDto.getBusClass())
                .stopStId(busRouteInfoDto.getStopStId())
                .stopEdId(busRouteInfoDto.getStopEdId())
                .build();
    }

    protected BusStopInfo busStopDtoToEntity(BusStopInfoDto busStopInfoDto){
        return BusStopInfo.builder()
                .busStopId(busStopInfoDto.getBusStopId())
                .busStopName(busStopInfoDto.getBusStopName())
                .busStopLon(busStopInfoDto.getBusStopLon())
                .busStopLat(busStopInfoDto.getBusStopLat())
                .busStopMark(busStopInfoDto.getBusStopMark())
                .build();
    }

}
