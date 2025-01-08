package com.dpod.buschat.businfo.controller;


import com.dpod.buschat.businfo.entity.Members;
import com.dpod.buschat.businfo.service.TestService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

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
    public ResponseEntity<String> saveBusStopInfo(){
        RestClient restClient = RestClient.create();
        String pageNo = "1";
        String numOfRows = "10";

        ResponseEntity<String> busstopInfo = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusStopInfo.xo?pageNo=" +
                        "{pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                          pageNo,numOfRows,secretkey)
                .retrieve()
                .toEntity(String.class);

        return busstopInfo;
    }




}
