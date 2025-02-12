package com.dpod.buschat.businfo.controller;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
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
        String pageNo = "1";
        String totalCount = "5000";

        busInfoSaveService.saveBusStopInfo(pageNo,totalCount);
    }


    /**
     * ---- 사용 주의 ----
     * 노선 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @PostMapping("/routeinfo/save")
    public void saveBusRouteInfo(){
        String pageNo = "1";
        String totalCount = "1000";

        busInfoSaveService.saveBusRouteInfo(pageNo,totalCount);
    }


    @PostMapping("/stoprouteinfo/save")
    public void saveBusStopRouteInfo(){
        String pageNo = "1";
        String totalCount = "100";

        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo("1", "1000");
        int busRouteTotalApi = Integer.parseInt(busRouteInfoDtoList.get(busRouteInfoDtoList.size() - 1).getRNum());
        //API 에서 버스 노선 개수를 가져오기 위한 호출

        int busRouteTotalDB = busRouteInfoService.countBusRouteInfo();
        //DB 에서 버스 노선 개수를 가져오기 위한 호출

        if(busRouteTotalApi!=busRouteTotalDB) {
            throw new BusInfoException(ErrorCode.ROUTE_COUNT_MISMATCH);
        }

        if(busInfoSearchService.searchBusStopRouteCount()==0){
            log.info("----------정류장 정차 노선 정보 저장 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i, pageNo, totalCount);

                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busInfoSaveService.saveBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }else{
            log.info("----------정류장 정차 노선 정보 업데이트 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i, pageNo, totalCount);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busInfoSaveService.updateBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }
    }


    private List<BusRouteRoadInfoDto> getBusRouteRoadInfoList(long i, String pageNo, String totalCount) {
        BusRouteInfoDto busRouteInfoDto = busInfoSearchService.searchBusRouteInfo(i);
        String routeId = busRouteInfoDto.getBrtId();
        return busInfoApiService.requestBusStopRouteInfo(routeId, pageNo, totalCount);
    }

}
