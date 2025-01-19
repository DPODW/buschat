package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusArrivalInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusRouteInfoXml;
import com.dpod.buschat.businfo.entity.Members;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoService;
import com.dpod.buschat.businfo.service.TestService;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusApiController {

    private final TestService  testService;

    private final BusInfoService busInfoService;

    private final BusStopRouteRepo busStopRouteRepo;

    @Value("${secretkey}")
    private String secretkey;

    public BusApiController(TestService testService, BusInfoService busInfoService, BusStopRouteRepo busStopRouteRepo) {
        this.testService = testService;
        this.busInfoService = busInfoService;
        this.busStopRouteRepo = busStopRouteRepo;
    }


    @GetMapping("/save/test")
    public void jpaSaveTest(){
        Members members = new Members();
        members.createMemberInfo("test1","test12");

        testService.testEntitySave(members);
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

        //RestClient - JAXB 사용시 : messageConverters 에 JAXB 를 설정해주어야 함. (List)
        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusStopInfoXml busStopInfoAllXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusStopInfo.xo?pageNo=" +
                            "{pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                          pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusStopInfoXml.class);

        List<BusStopInfoDto> busStopInfoDtoList = busStopInfoAllXml.getBusStopInfoXmlList().getBusStopInfoDtoList();

        busInfoService.saveBusStopInfo(busStopInfoDtoList);
    }


    /**
     * ---- 사용 주의 ----
     * 노선 정보 API 데이터를 받아와서 전부 DB 에 저장하는 컨트롤러
     * 추후 데이터 유무를 기준으로 UPDATE 처리 기능 추가할 예정
     * 현재 실행시 동일한 데이터가 중복 저장되니 요청 주의 필요
     **/
    @GetMapping("/busLineInfo/save")
    public void saveBusRouteInfo(){
        String pageNo = "1";
        String totalCount = "500";

        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusRouteInfoXml busRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/RouteInfo.xo?" +
                        "pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusRouteInfoXml.class);

        List<BusRouteInfoDto> busRouteInfoDtoList = busRouteInfoXml.getBusRouteInfoXmlList().getBusRouteInfoDtoList();

        busInfoService.saveBusRouteInfo(busRouteInfoDtoList);
    }


    @PostMapping("/stopinfo/search")
    @ResponseBody
    public List<BusStopInfoDto> searchBusStopInfo(@RequestBody HashMap<String, Object> busStopNameMap){
        String busStopName = busStopNameMap.get("busStopName").toString();

        return busInfoService.searchBusStopInfo(busStopName);
    }


    @PostMapping("/stopinfo/arrival")
    @ResponseBody
    public List<BusArrivalInfoDto> searchBusArrivalInfo(@RequestBody HashMap<String,Object> busStopIdMap){
        String busStopId = busStopIdMap.get("busStopId").toString();

        String pageNo = "1";
        String numOfRows = "10";

        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusArrivalInfoXml busArrivalInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/getBusArrivalInfo.xo?stopid={stopid}&" +
                                "pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busStopId,pageNo,numOfRows,secretkey)
                .retrieve()
                .body(BusArrivalInfoXml.class);

        return busArrivalInfoXml.getBusArrivalInfoXmlList().getBusArrivalInfoDtoList();
    }


    @GetMapping("/stoprouteinfo/search")
    @ResponseBody
    public void searchBusStopRouteInfo(){

        List<BusStopRouteInfoDto> busStopRouteInfoDtoList = new ArrayList<>();

        String brtNo = null;

        for (int i = 1; i <=50; i++) {
            //todo:최대값 하드코딩 -> 동적으로 최대치를 가져오도록 코드 수정 필요
            BusRouteInfoDto busRouteInfoDto = busInfoService.searchBusRouteInfo((long) i);

            String routeId = busRouteInfoDto.getBrtId();
             brtNo = busRouteInfoDto.getBrtNo();

            //노선 정보를 배열에 담아서 가져오면
            busStopRouteInfoDtoList = busInfoService.reqBusStopRouteInfoApi(secretkey, routeId);

            //해당 배열을 순회하면서 -> 배열 정류장 ID == 저장된 정류장 ID 가 있을 시(조건문 구현 필요) -> 새로 추가된 컬럼에 brtNo 을 추가해주어야함
        }
        log.info("request busStopRouteInfo : {}",busStopRouteInfoDtoList);

        for(BusStopRouteInfoDto busStopRouteInfoDto : busStopRouteInfoDtoList){
            busStopRouteRepo.saveBusStopRoute(busStopRouteInfoDto.getStopId(),brtNo);
        }
    }

}
