package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.*;
import com.dpod.buschat.businfo.dto.xml.*;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.ApiReqParam;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
public class BusInfoApiServiceImpl implements BusInfoApiService {

    @Value("${secretkey}")
    private String secretkey;

    private final BusTimeTableService busTimeTableService;

    private final BusRouteInfoRepo busRouteInfoRepo;

    public BusInfoApiServiceImpl(BusTimeTableService busTimeTableService, BusRouteInfoRepo busRouteInfoRepo) {
        this.busTimeTableService = busTimeTableService;
        this.busRouteInfoRepo = busRouteInfoRepo;
    }

    @Override
    public List<BusStopInfoDto> requestBusStopInfo() {
        RestClient restClient = getRestClient();

        BusStopInfoXml busStopInfoAllXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusStopInfo.xo?pageNo=" +
                                "{pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT,secretkey)
                .retrieve()
                .body(BusStopInfoXml.class);
        return busStopInfoAllXml.getBusStopInfoXmlList().getBusStopInfoDtoList();
    }


    @Override
    public List<BusRouteInfoDto> requestBusRouteInfo() {
        RestClient restClient = getRestClient();

        BusRouteInfoXml busRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/RouteInfo.xo?" +
                        "pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}"
                        ,ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT,secretkey)
                .retrieve()
                .body(BusRouteInfoXml.class);

        return busRouteInfoXml.getBusRouteInfoXmlList().getBusRouteInfoDtoList();
    }


    @Override
    public List<BusRouteRoadInfoDto> requestBusStopRouteInfo(String routeId) {
        RestClient restClient = getRestClient();

        BusStopRouteInfoXml busStopRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/AllRouteDetailInfo.xo?" +
                                "routeid={routeId}&pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",
                        routeId,ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT,secretkey)
                .retrieve()
                .body(BusStopRouteInfoXml.class);

        return busStopRouteInfoXml.getBusStopRouteInfoXmlList().getBusRouteRoadInfoDtoList();
    }


    @Override
    public List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId) {
        RestClient restClient = getRestClient();

        BusArrivalInfoXml busArrivalInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/getBusArrivalInfo.xo?stopid={stopid}&" +
                                "pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busStopId,ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT,secretkey)
                .retrieve()
                .body(BusArrivalInfoXml.class);
        //운행중인 버스 정보가 없으시, 해당 정류장 정보를 리턴함

        return busArrivalInfoXml.getBusArrivalInfoXmlList().getBusArrivalInfoDtoList();
    }

    @Override
    public List<BusTimeTableInfoDto> requestBusTimeTableInfo(String busRouteName) {
        String busRouteNumber = busRouteName.replaceAll("\\D", "");
        //방면 정보 제거 (방면 정보가 없는 순수 노선 번호 [ex)773] 으로 요청을 해야 정상 응답)
        //TODO: 방면 정보에 숫자가 있으면 오류 발생 (해당 숫자가 노선 번호에 붙음) 처리 필요 (테스트 코드 참고)
        //TODO: 마을 버스 [ex) 울주02] 노선도 처리 필요

        //TODO: 검색이 안되는 노선 정보 존재 EX)2100(노포동역 방면) 처리 필요
        //TODO: 모든 노선으로 요청해보고, 잘못된 노선 색출 필요
        RestClient restClient = getRestClient();
        BusTimeTableInfoXml busTimeTableInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusTimetable.xo?routeNo={routeNo}&" +
                                "dayOfWeek={dayOfWeek}&pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busRouteNumber, "0", ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT, secretkey)
                .retrieve()
                .body(BusTimeTableInfoXml.class);

        return busTimeTableInfoXml.getBusTimeTableInfoList().getBusTimeTableInfoList();
    }

    private static RestClient getRestClient() {
        return RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();
    }


    @Override
    public void checkOtherRouteName() {
        for(int i=1; i<=busRouteInfoRepo.countAllBy(); i++){
            BusRouteInfo busRouteInfoBySequence = busRouteInfoRepo.findBusRouteInfosBySequence((long)i);
            List<BusTimeTableInfoDto> busTimeTableInfoDtoList = requestBusTimeTableInfo(busRouteInfoBySequence.getBrtName());

            if(busTimeTableInfoDtoList==null){
                log.info("getBrtName : {}",busRouteInfoBySequence.getBrtName());
                log.info("반복횟수 {}",i);
            }
            if(busTimeTableInfoDtoList!= null&&busTimeTableService.deleteAnotherDir(busTimeTableInfoDtoList, busRouteInfoBySequence.getBrtName()).isEmpty()){
                log.info("시간표에 없는 방면 정보 : {}",busRouteInfoBySequence.getBrtName());
            }
        }
    }



}
