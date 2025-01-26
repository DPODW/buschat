package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusArrivalInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusRouteInfoXml;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import com.dpod.buschat.businfo.service.BusInfoService;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class BusApiController {

    private final BusInfoService busInfoService;

    public BusApiController(BusInfoService busInfoService) {
        this.busInfoService = busInfoService;
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

}
