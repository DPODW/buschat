package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.*;
import com.dpod.buschat.businfo.dto.xml.*;
import com.dpod.buschat.businfo.service.ApiReqParam;
import com.dpod.buschat.businfo.service.BusInfoApiService;
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
        RestClient restClient = getRestClient();
        BusTimeTableInfoXml busTimeTableInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusTimetable.xo?routeNo={routeNo}&" +
                                "dayOfWeek={dayOfWeek}&pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busRouteName, "0", ApiReqParam.BUS_INFO_PAGE_NO,ApiReqParam.BUS_INFO_TOTAL_COUNT, secretkey)
                .retrieve()
                .body(BusTimeTableInfoXml.class);

        return busTimeTableInfoXml.getBusTimeTableInfoList().getBusTimeTableInfoList();
    }

    private static RestClient getRestClient() {
        return RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();
    }

}
