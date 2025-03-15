package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusRouteInfoService {

    void saveBusRouteInfo(String pageNo,String totalCount);

    void updateBusRouteInfo(String pageNo,String totalCount);

    List<BusRouteInfo> createBusRouteInfoEntity(String pageNo,String totalCount);

    List<BusArrivalInfoDto> plusInfoToBusRouteNm(List<BusArrivalInfoDto> busArrivalInfoDtoList);

    int countBusRouteInfo();

}
