package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.exception.bus.BusInfoException;
import com.dpod.buschat.businfo.exception.bus.ErrorCode;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import com.dpod.buschat.businfo.service.BusRouteInfoService;
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

    private static final String TOTAL_COUNT = "5000";

    private static final String PAGE_NO = "1";

    private final BusInfoSaveService busInfoSaveService;
    
    private final BusInfoSearchService busInfoSearchService;
    
    private final BusInfoApiService busInfoApiService;

    private final BusRouteInfoService busRouteInfoService;


    public BusInfoSaveController(BusInfoSaveService busInfoSaveService, BusInfoSearchService busInfoSearchService, BusInfoApiService busInfoApiService, BusRouteInfoService busRouteInfoService) {
        this.busInfoSaveService = busInfoSaveService;
        this.busInfoSearchService = busInfoSearchService;
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoService = busRouteInfoService;
    }

    /**
     * ---- 사용 주의 ----
     * 버스 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @PostMapping("/stopinfo/save")
    public void saveBusStopInfo(){
        busInfoSaveService.saveBusStopInfo(PAGE_NO,TOTAL_COUNT);
    }


    /**
     * ---- 사용 주의 ----
     * 노선 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @PostMapping("/routeinfo/save")
    public void saveBusRouteInfo(){
        if(busRouteInfoService.countBusRouteInfo()==0){
            busRouteInfoService.saveBusRouteInfo(PAGE_NO,TOTAL_COUNT);
        }else
            busRouteInfoService.updateBusRouteInfo(PAGE_NO,TOTAL_COUNT);
    }



    @PostMapping("/stoprouteinfo/save")
    public void saveBusStopRouteInfo(){
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo(PAGE_NO, TOTAL_COUNT);
        int busStopTotalApi = Integer.parseInt(busStopInfoDtoList.get(busStopInfoDtoList.size() - 1).getRNum())+1;
        //시티버스 정류장 정보는 API 에서 제공 하지 않기 때문에, 시티버스 정류장 정보 1건을 API 결과에 더해줌
        int busStopTotalDB = busInfoSearchService.searchBusStopInfoCount();


        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo(PAGE_NO, TOTAL_COUNT);
        int busRouteTotalApi = Integer.parseInt(busRouteInfoDtoList.get(busRouteInfoDtoList.size() - 1).getRNum());
        int busRouteTotalDB = busRouteInfoService.countBusRouteInfo();

        if(busStopTotalApi!=busStopTotalDB){
            log.error("API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다름. API 제공 정류장 개수= {} / DB 저장 정류장 개수 = {}", busStopTotalApi,busStopTotalDB);
            throw new BusInfoException(ErrorCode.BUSSTOP_COUNT_MISMATCH);
        }

        if(busRouteTotalApi!=busRouteTotalDB) {
            log.error("API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다 API 제공 노선 개수= {} / DB 저장 노선 개수 = {}", busRouteTotalApi,busRouteTotalDB);
            throw new BusInfoException(ErrorCode.ROUTE_COUNT_MISMATCH);
        }

        if(busInfoSearchService.searchBusStopRouteCount()==0){
            log.info("----------정류장 정차 노선 정보 저장 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busInfoSaveService.saveBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }else{
            log.info("----------정류장 정차 노선 정보 업데이트 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busInfoSaveService.updateBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }
    }


    private List<BusRouteRoadInfoDto> getBusRouteRoadInfoList(long i) {
        BusRouteInfoDto busRouteInfoDto = busInfoSearchService.searchBusRouteInfo(i);
        String routeId = busRouteInfoDto.getBrtId();
        return busInfoApiService.requestBusStopRouteInfo(routeId, PAGE_NO, TOTAL_COUNT);
    }

}
