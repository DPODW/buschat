package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusRouteInfoXml;
import com.dpod.buschat.businfo.dto.xml.BusStopInfoXml;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusInfoRepository;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BusInfoSaveServiceImpl implements BusInfoSaveService {

    private final BusInfoApiService busInfoApiService;

    private final BusInfoRepository busInfoRepository;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToEntityConvert toEntityConvert;


    public BusInfoSaveServiceImpl(BusInfoApiService busInfoApiService, BusInfoRepository busInfoRepository, BusRouteInfoRepo busRouteInfoRepo1, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busInfoRepository = busInfoRepository;
        this.busRouteInfoRepo = busRouteInfoRepo1;
        this.toEntityConvert = toEntityConvert;
    }


    @Override
    public void saveBusStopInfo(String pageNo, String totalCount) {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo(pageNo, totalCount);

        busStopInfoDtoList.forEach( //foreach : stream 생략 가능
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busInfoRepository.save(busStopInfo);
                });
    }

    @Override
    public void saveBusRouteInfo(String pageNo, String totalCount) {
        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo(pageNo, totalCount);

        busRouteInfoDtoList.forEach( //foreach : stream 생략 가능
                busRouteInfoDto -> {
                    BusRouteInfo busRouteInfo = toEntityConvert.busRouteDtoToEntity(busRouteInfoDto);
                    busRouteInfoRepo.save(busRouteInfo);
                });
    }


    @Override
    public void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto) {

    }
}
