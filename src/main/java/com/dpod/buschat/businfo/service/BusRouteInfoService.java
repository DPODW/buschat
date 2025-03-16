package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusRouteInfoService {

    void saveBusRouteInfo();

    void updateBusRouteInfo();

    List<BusRouteInfo> createBusRouteInfoEntity();

    List<BusArrivalInfoDto> plusInfoToBusRouteNm(List<BusArrivalInfoDto> busArrivalInfoDtoList);

    int countBusRouteInfo();

}
