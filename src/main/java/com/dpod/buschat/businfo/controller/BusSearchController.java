package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusSearchController {

    private final BusInfoSearchService busInfoSearchService;

    private final BusInfoApiService busInfoApiService;


    public BusSearchController(BusInfoSearchService busInfoSearchService, BusInfoApiService busInfoApiService) {
        this.busInfoSearchService = busInfoSearchService;
        this.busInfoApiService = busInfoApiService;
    }

    /*
    * TODO: RESTFUL 한 개발을 위해선
    *  1. POST -> GET 변경 필요
    *  2. RequestBody -> RequestParam 으로 변경 필요 (GET 요청은 RequestBody 사용 불가)
    *  3. URL 동사 불필요
    * */

    @PostMapping("/stopinfo/search")
    @ResponseBody
    public List<BusStopInfoDto> searchBusStopInfo(@RequestBody HashMap<String, Object> busStopNameMap){
        String busStopName = busStopNameMap.get("busStopName").toString();

        return busInfoSearchService.searchBusStopInfo(busStopName);
    }


    @PostMapping("/stopinfo/arrival")
    @ResponseBody
    public List<BusArrivalInfoDto> searchBusArrivalInfo(@RequestBody HashMap<String,Object> busStopIdMap){
        String busStopId = busStopIdMap.get("busStopId").toString();

        String pageNo = "1";
        String numOfRows = "10";

        return busInfoApiService.requestBusArrivalInfo(busStopId, pageNo, numOfRows);
    }

    
}
