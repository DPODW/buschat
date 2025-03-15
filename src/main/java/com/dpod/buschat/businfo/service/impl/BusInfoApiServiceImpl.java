package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.*;
import com.dpod.buschat.businfo.dto.xml.*;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BusInfoApiServiceImpl implements BusInfoApiService {

    @Value("${secretkey}")
    private String secretkey;

    private static RestClient getRestClient() {
        return RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();
    }

    @Override
    public List<BusStopInfoDto> requestBusStopInfo(String pageNo, String totalCount) {
        RestClient restClient = getRestClient();

        BusStopInfoXml busStopInfoAllXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusStopInfo.xo?pageNo=" +
                                "{pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusStopInfoXml.class);
        return busStopInfoAllXml.getBusStopInfoXmlList().getBusStopInfoDtoList();
    }


    @Override
    public List<BusRouteInfoDto> requestBusRouteInfo(String pageNo, String totalCount) {
        RestClient restClient = getRestClient();

        BusRouteInfoXml busRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/RouteInfo.xo?" +
                        "pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusRouteInfoXml.class);

        return busRouteInfoXml.getBusRouteInfoXmlList().getBusRouteInfoDtoList();
    }


    @Override
    public List<BusRouteRoadInfoDto> requestBusStopRouteInfo(String routeId, String pageNo, String totalCount) {
        RestClient restClient = getRestClient();

        BusStopRouteInfoXml busStopRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/AllRouteDetailInfo.xo?" +
                                "routeid={routeId}&pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",
                        routeId,pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusStopRouteInfoXml.class);

        return busStopRouteInfoXml.getBusStopRouteInfoXmlList().getBusRouteRoadInfoDtoList();
    }


    @Override
    public List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId,String pageNo, String totalCount) {
        RestClient restClient = getRestClient();

        BusArrivalInfoXml busArrivalInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/getBusArrivalInfo.xo?stopid={stopid}&" +
                                "pageNo={pageNo}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busStopId,pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusArrivalInfoXml.class);
        //운행중인 버스 정보가 없으시, 해당 정류장 정보를 리턴함

        return busArrivalInfoXml.getBusArrivalInfoXmlList().getBusArrivalInfoDtoList();
    }

    @Override
    public List<BusTimeTableInfoDto> requestBusTimeTableInfo(String busRouteName) {
        String busRouteNumber = busRouteName.replaceAll("\\D", "");
        //방면 정보 제거 (방면 정보가 없는 순수 노선 번호 [ex)773] 으로 요청을 해야 정상 응답)

        //TODO: 검색이 안되는 노선 정보 존재 EX)2100(노포동역 방면) 처리 필요
        RestClient restClient = getRestClient();
        BusTimeTableInfoXml busTimeTableInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/BusTimetable.xo?routeNo={routeNo}&" +
                                "dayOfWeek={dayOfWeek}&numOfRows={numOfRows}&serviceKey={serviceKey}",
                        busRouteNumber, "0", 100, secretkey)
                .retrieve()
                .body(BusTimeTableInfoXml.class);

        return busTimeTableInfoXml.getBusTimeTableInfoList().getBusTimeTableInfoList();
    }
}
