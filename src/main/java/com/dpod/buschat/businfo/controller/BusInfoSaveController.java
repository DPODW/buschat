package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.exception.bus.BusInfoException;
import com.dpod.buschat.businfo.exception.bus.ErrorCode;
import com.dpod.buschat.businfo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final BusTimeTableService busTimeTableService;

    public BusInfoSaveController(BusStopInfoService busStopInfoService, BusRouteInfoService busRouteInfoService, BusTimeTableService busTimeTableService) {
        this.busStopInfoService = busStopInfoService;
        this.busRouteInfoService = busRouteInfoService;
        this.busTimeTableService = busTimeTableService;
    }

    @PostMapping("/stopinfo")
    public void saveBusStop(){
        busStopInfoService.saveBusStopInfo();
    }


    @PostMapping("/routeinfo")
    public void saveBusRoute(){
         busRouteInfoService.saveBusRouteInfo();
    }

    @PostMapping("/stoprouteinfo")
    public void saveBusStopRoute(){
        busStopInfoService.saveBusStopRouteInfo();
    }


    @PostMapping("/timetable")
    public void saveBusTimeTableInfo() {
        busTimeTableService.saveTimeTableInfo();
    }

}
