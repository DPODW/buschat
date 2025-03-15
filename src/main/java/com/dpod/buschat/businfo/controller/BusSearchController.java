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

    private static final String TOTAL_COUNT = "50";

    private static final String PAGE_NO = "1";

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
        List<BusArrivalInfoDto> busArrivalInfoDtoList = busRouteInfoService.plusInfoToBusRouteNm(busInfoApiService.requestBusArrivalInfo(busStopId, PAGE_NO, TOTAL_COUNT));
        List<BusStopRouteInfoDto> busStopRouteInfoList = busInfoSearchService.searchBusStopInfoToId(busStopId).getBusStopRouteInfoList();
        return busStopInfoService.createBusAllArrivalList(busArrivalInfoDtoList, busStopRouteInfoList);
    }

    @GetMapping("/stopinfo/timetable/{busRouteName}")
    public List<BusTimeTableInfoDto> searchBusTimeTableInfo(@PathVariable("busRouteName") String busRouteName) {
        /// 테스트용 컨트롤러
        ///  버스 방면 정보 붙은채로 API 에 요청
        List<BusTimeTableInfoDto> busTimeTableInfoDtoList = busInfoApiService.requestBusTimeTableInfo(busRouteName);
        return busTimeTableService.deleteAnotherDir(busTimeTableInfoDtoList, busRouteName);
    }
}