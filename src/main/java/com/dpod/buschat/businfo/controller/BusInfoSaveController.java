package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusInfoSaveController {

    private final BusInfoSaveService busInfoSaveService;
    
    private final BusInfoSearchService busInfoSearchService;
    
    private final BusInfoApiService busInfoApiService;


    public BusInfoSaveController(BusInfoSaveService busInfoSaveService, BusInfoSearchService busInfoSearchService, BusInfoApiService busInfoApiService) {
        this.busInfoSaveService = busInfoSaveService;
        this.busInfoSearchService = busInfoSearchService;
        this.busInfoApiService = busInfoApiService;
    }

    /**
     * ---- 사용 주의 ----
     * 버스 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @GetMapping("/stopinfo/save")
    public void saveBusStopInfo(){
        String pageNo = "1";
        String totalCount = "3542";

        busInfoSaveService.saveBusStopInfo(pageNo,totalCount);
    }


    /**
     * ---- 사용 주의 ----
     * 노선 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @GetMapping("/routeinfo/save")
    public void saveBusRouteInfo(){
        String pageNo = "1";
        String totalCount = "500";

        busInfoSaveService.saveBusRouteInfo(pageNo,totalCount);
    }


    /**
     * ---- 사용 주의 ----
     * 정류장 도착 노선 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @GetMapping("/stoprouteinfo/save")
    public void saveBusStopRouteInfo(){
        //TODO: 25-02-28 기준 노선 3개 추가됌. (UPDATE 필요)
        int busStopTotalCnt = 488;
        String pageNo = "1";
        String totalCount = "100";

        for (int i = 1; i <=busStopTotalCnt; i++) {
            BusRouteInfoDto busRouteInfoDto = busInfoSearchService.searchBusRouteInfo((long) i);
            String routeId = busRouteInfoDto.getBrtId();

            List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = busInfoApiService.requestBusStopRouteInfo(routeId,pageNo,totalCount);

            for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                busInfoSaveService.saveBusStopRoute(busRouteRoadInfoDto);
            }
        }
    }

}
