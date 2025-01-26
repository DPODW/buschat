package com.dpod.buschat.businfo.service;

import com.dpod.buschat.businfo.dto.BusArrivalInfoDto;
import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusRouteInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public interface BusInfoApiService {

    List<BusStopInfoDto> requestBusStopInfo(String pageNo, String totalCount);

    List<BusRouteInfoDto> requestBusRouteInfo(String pageNo, String totalCount);

    List<BusStopRouteInfoDto> requestBusStopRouteInfo(String routeId, String pageNo, String totalCount);

    List<BusArrivalInfoDto> requestBusArrivalInfo(String busStopId,String pageNo, String numOfRows);


}
