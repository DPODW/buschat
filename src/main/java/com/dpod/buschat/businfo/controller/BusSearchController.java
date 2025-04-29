package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusSearchController {

    private final BusInfoSearchService busInfoSearchService;

    private final BusStopInfoService busStopInfoService;

    private final BusInfoApiService busInfoApiService;

    private final BusRouteInfoService busRouteInfoService;


    public BusSearchController(BusInfoSearchService busInfoSearchService, BusStopInfoService busStopInfoService, BusInfoApiService busInfoApiService, BusRouteInfoService busRouteInfoService) {
        this.busInfoSearchService = busInfoSearchService;
        this.busStopInfoService = busStopInfoService;
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoService = busRouteInfoService;
    }


    @GetMapping("/stopinfo/{busStopName}")
    public List<BusStopInfoDto> searchBusStopInfo(@PathVariable("busStopName") String busStopName) {
        return busInfoSearchService.searchBusStopInfoToHgl(busStopName);
    }


    @GetMapping("/stopinfo/arrival/{busStopId}")
    public List<BusArrivalInfoDto> searchBusArrivalInfo(@PathVariable("busStopId") String busStopId){
        List<BusArrivalInfoDto> apiReqArrivalInfo = busInfoApiService.requestBusArrivalInfo(busStopId);
        List<BusStopRouteInfoDto> busStopRouteInfoList = busInfoSearchService.searchBusStopInfoToId(busStopId).getBusStopRouteInfoList();

        if(apiReqArrivalInfo.get(0).getBusRouteId()==null){
            return busStopInfoService.createInactiveBusList(busStopRouteInfoList);
            //API 요청 결과 : 0번째 요소 없을 시 해당 정류장 도착 정보 없음으로 판단 -> 모든 버스 출발 예정시간 만듬
        }

        List<BusArrivalInfoDto> busArrivalInfoDtoList = busRouteInfoService.plusInfoToBusRouteNm(busInfoApiService.requestBusArrivalInfo(busStopId));
        return busStopInfoService.createBusAllArrivalList(busArrivalInfoDtoList, busStopRouteInfoList);
    }
}