package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.exception.BusInfoException;
import com.dpod.buschat.businfo.exception.ErrorCode;
import com.dpod.buschat.businfo.response.BusInfoSaveRep;
import com.dpod.buschat.businfo.response.RepCode;
import com.dpod.buschat.businfo.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<BusInfoSaveRep> saveBusStop(){
        busStopInfoService.saveBusStopInfo();
        return BusInfoSaveRep.SuccessResponse(RepCode.BUSSTOP_INFO_SAVE);
    }


    @PostMapping("/routeinfo")
    public ResponseEntity<BusInfoSaveRep> saveBusRoute(){
        busRouteInfoService.saveBusRouteInfo();
        return BusInfoSaveRep.SuccessResponse(RepCode.BUSROUTE_INFO_SAVE);
    }

    @PostMapping("/stoprouteinfo")
    public ResponseEntity<BusInfoSaveRep> saveBusStopRoute(){
        busStopInfoService.saveBusStopRouteInfo();
        return BusInfoSaveRep.SuccessResponse(RepCode.BUSSTOP_ROUTE_INFO_SAVE);
    }


    @PostMapping("/timetable")
    public ResponseEntity<BusInfoSaveRep> saveBusTimeTableInfo() {
        busTimeTableService.saveTimeTableInfo();
        return BusInfoSaveRep.SuccessResponse(RepCode.BUSSTOP_TIMETABLE_INFO_SAVE);
    }
}
