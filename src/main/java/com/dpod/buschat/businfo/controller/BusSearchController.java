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

    private final BusTimeTableService busTimeTableService;


    public BusSearchController(BusInfoSearchService busInfoSearchService, BusStopInfoService busStopInfoService, BusInfoApiService busInfoApiService, BusRouteInfoService busRouteInfoService, BusTimeTableService busTimeTableService) {
        this.busInfoSearchService = busInfoSearchService;
        this.busStopInfoService = busStopInfoService;
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoService = busRouteInfoService;
        this.busTimeTableService = busTimeTableService;
    }


    @GetMapping("/stopinfo/{busStopName}")
    public List<BusStopInfoDto> searchBusStopInfo(@PathVariable("busStopName") String busStopName) {
        return busInfoSearchService.searchBusStopInfoToHgl(busStopName);
    }


    @GetMapping("/stopinfo/arrival/{busStopId}")
    public List<BusArrivalInfoDto> searchBusArrivalInfo(@PathVariable("busStopId") String busStopId){
        List<BusArrivalInfoDto> busArrivalInfoDtoList = busRouteInfoService.plusInfoToBusRouteNm(busInfoApiService.requestBusArrivalInfo(busStopId));
        List<BusStopRouteInfoDto> busStopRouteInfoList = busInfoSearchService.searchBusStopInfoToId(busStopId).getBusStopRouteInfoList();
        return busStopInfoService.createBusAllArrivalList(busArrivalInfoDtoList, busStopRouteInfoList);
    }

    /// 버스 시간표 테스트용 컨트롤러
    @GetMapping("/stopinfo/timetable/test")
    public void searchBusTimeTableInfo() {
        busTimeTableService.saveTimeTableInfo();
    }
}