package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import com.dpod.buschat.businfo.service.BusStopInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusSearchController {

    private static final String TOTAL_COUNT = "50";

    private static final String PAGE_NO = "1";

    private final BusInfoSearchService busInfoSearchService;

    private final BusStopInfoService busStopInfoService;

    private final BusInfoApiService busInfoApiService;


    public BusSearchController(BusInfoSearchService busInfoSearchService, BusStopInfoService busStopInfoService, BusInfoApiService busInfoApiService) {
        this.busInfoSearchService = busInfoSearchService;
        this.busStopInfoService = busStopInfoService;
        this.busInfoApiService = busInfoApiService;
    }


    @GetMapping("/stopinfo/{busStopName}")
    public List<BusStopInfoDto> searchBusStopInfo(@PathVariable("busStopName") String busStopName) {
        return busInfoSearchService.searchBusStopInfoToHgl(busStopName);
    }


    @GetMapping("/stopinfo/arrival/{busStopId}")
    public List<BusArrivalInfoDto> searchBusArrivalInfo(@PathVariable("busStopId") String busStopId){
        List<BusArrivalInfoDto> busArrivalInfoDtoList = busInfoApiService.requestBusArrivalInfo(busStopId, PAGE_NO, TOTAL_COUNT);
        //운행중인 버스가 없으면 정류장 정보를 반환 (API 에서 그렇게 제공함)

        List<BusStopRouteInfoDto> busStopRouteInfoList = busInfoSearchService.searchBusStopInfoToId(busStopId).getBusStopRouteInfoList();
        return busStopInfoService.createBusAllArrivalList(busArrivalInfoDtoList, busStopRouteInfoList);
    }
}