package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusArrivalInfoXml;
import com.dpod.buschat.businfo.entity.Members;
import com.dpod.buschat.businfo.service.BusInfoService;
import com.dpod.buschat.businfo.service.TestService;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusApiController {

    private final TestService  testService;

    private final BusInfoService busInfoService;

    @Value("${secretkey}")
    private String secretkey;

    public BusApiController(TestService testService, BusInfoService busInfoService) {
        this.testService = testService;
        this.busInfoService = busInfoService;
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
    * 현재 실행시 동일한 데이터가 중복 저장되니 요청
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


    @GetMapping("/stopinfo/search")
    @ResponseBody
    public List<BusStopInfoDto> searchBusStopInfo(){
        //front 단에서 값 넘겨주어야함 (AXIOS) / sample 데이터
        String busStopName = "대산빌라";
        return busInfoService.searchBusStopInfo(busStopName);
    }


    @GetMapping("/stopinfo/arrival")
    @ResponseBody
    public List<BusArrivalInfoDto> searchBusArrivalInfo(){
        //sample 데이터
        String stopId = "196040122";
        String pageNo = "1";
        String numOfRows = "10";

        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusArrivalInfoXml busArrivalInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/getBusArrivalInfo.xo?stopid={stopid}&" +
                                "pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        stopId,pageNo,numOfRows,secretkey)
                .retrieve()
                .body(BusArrivalInfoXml.class);

        return busArrivalInfoXml.getBusArrivalInfoXmlList().getBusArrivalInfoDtoList();
    }

}



