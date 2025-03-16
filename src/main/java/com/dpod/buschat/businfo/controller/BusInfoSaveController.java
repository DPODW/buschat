package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.exception.bus.BusInfoException;
import com.dpod.buschat.businfo.exception.bus.ErrorCode;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusStopInfoService;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import com.dpod.buschat.businfo.service.BusRouteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusInfoSaveController {

    private final BusStopInfoService busStopInfoService;

    private final BusRouteInfoService busRouteInfoService;


    public BusInfoSaveController(BusStopInfoService busStopInfoService, BusRouteInfoService busRouteInfoService) {
        this.busStopInfoService = busStopInfoService;
        this.busRouteInfoService = busRouteInfoService;
    }

    @PostMapping("/stopinfo")
    public void saveBusStop(){
        if(busStopInfoService.countBusStopInfo()==0){
            log.info("----------정류장 정보 저장 시작----------");
            busStopInfoService.saveBusStopInfo();
        }else{
            log.info("----------정류장 정보 업데이트 시작 (업데이트 후 정차 노선 저장 API 호출 필요)----------");
            busStopInfoService.updateBusStopInfo();
        }
    }


    @PostMapping("/routeinfo")
    public void saveBusRoute(){
        if(busRouteInfoService.countBusRouteInfo()==0){
            log.info("----------노선 정보 저장 시작----------");
            busRouteInfoService.saveBusRouteInfo();
        }else{
            log.info("----------노선 정보 업데이트 시작----------");
            busRouteInfoService.updateBusRouteInfo();
        }
    }

    @PostMapping("/stoprouteinfo")
    public void saveBusStopRoute(){
        busStopInfoService.saveBusStopRouteInfo();
    }

}
