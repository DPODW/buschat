package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.entity.Members;
import com.dpod.buschat.businfo.service.TestService;

import com.dpod.buschat.businfo.vo.BusStopInfoDto;
import com.dpod.buschat.businfo.vo.xml.BusStopInfoXml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RestController
public class BusApiController {

    private final TestService  testService;

    @Value("${secretkey}")
    private String secretkey;

    public BusApiController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping("/save/test")
    public void jpaSaveTest(){
        Members members = new Members();
        members.createMemberInfo("test1","test12");

        testService.testEntitySave(members);
    }

    @GetMapping("/stopinfo/save")
    public List<BusStopInfoDto> saveBusStopInfo(){
        String pageNo = "1";
        String numOfRows = "10";

        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusStopInfoXml busStopInfoAllXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusStopInfo.xo?pageNo=" +
                        "{pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                          pageNo,numOfRows,secretkey)
                .retrieve()
                .body(BusStopInfoXml.class);

        List<BusStopInfoDto> busStopInfoDtoList = busStopInfoAllXml.getBusStopInfoXmlList().getBusStopInfoDtoList();

        log.info("XML -> DTO : {}", busStopInfoDtoList);

        return busStopInfoDtoList;
    }

}
