package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusArrivalInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusRouteInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusStopRouteInfoXml;
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

    @Override
    public List<BusStopInfoDto> requestBusStopInfo(String pageNo, String totalCount) {
        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

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
        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusRouteInfoXml busRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/RouteInfo.xo?" +
                        "pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusRouteInfoXml.class);

        return busRouteInfoXml.getBusRouteInfoXmlList().getBusRouteInfoDtoList();
    }


    @Override
    public List<BusStopRouteInfoDto> requestBusStopRouteInfo(String routeId, String pageNo, String totalCount) {
        RestClient restClient = RestClient.builder()
                .messageConverters(List.of(new Jaxb2RootElementHttpMessageConverter()))
                .build();

        BusStopRouteInfoXml busStopRouteInfoXml = restClient.get()
                .uri("http://openapi.its.ulsan.kr/UlsanAPI/AllRouteDetailInfo.xo?" +
                                "routeid={routeId}&pageNo={pageNo}&numOfRows={totalCount}&serviceKey={secretkey}",
                        routeId,pageNo,totalCount,secretkey)
                .retrieve()
                .body(BusStopRouteInfoXml.class);

        return busStopRouteInfoXml.getBusStopRouteInfoXmlList().getBusStopRouteInfoDtoList();
    }


    @Override
    public List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId,String pageNo, String numOfRows) {
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
