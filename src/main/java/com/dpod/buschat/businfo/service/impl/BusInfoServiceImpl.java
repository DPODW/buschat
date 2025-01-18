package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.dto.xml.BusStopRouteInfoXml;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusInfoRepository;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoService;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Slf4j
@Service
public class BusInfoServiceImpl implements BusInfoService {

    private final BusInfoRepository busInfoRepository;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToEntityConvert toEntityConvert;

    private final ToDtoConvert toDtoConvert;

    public BusInfoServiceImpl(BusInfoRepository busInfoRepository, BusRouteInfoRepo busRouteInfoRepo, ToEntityConvert toEntityConvert, ToDtoConvert toDtoConvert) {
        this.busInfoRepository = busInfoRepository;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.toEntityConvert = toEntityConvert;
        this.toDtoConvert = toDtoConvert;
    }


    @Override
    public void saveBusStopInfo(List<BusStopInfoDto> busStopInfoDtoList) {
        busStopInfoDtoList.forEach( //foreach : stream 생략 가능
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busInfoRepository.save(busStopInfo);
                });
    }

    @Override
    public void saveBusRouteInfo(List<BusRouteInfoDto> busRouteInfoDtoList) {
        busRouteInfoDtoList.forEach( //foreach : stream 생략 가능
                busRouteInfoDto -> {
                    BusRouteInfo busRouteInfo = toEntityConvert.busRouteDtoToEntity(busRouteInfoDto);
                    busRouteInfoRepo.save(busRouteInfo);
                });
    }

    @Override
    public List<BusStopInfoDto> searchBusStopInfo(String busStopName) {
        List<BusStopInfo> allInfoByBusStopName = busInfoRepository.findAllByBusStopNameLike("%"+busStopName+"%");

        return allInfoByBusStopName.stream()
                .map(busStopInfo -> {
                            return toDtoConvert.busStopEntityToDto(busStopInfo);
                        }
                ).toList();
    }

    @Override
    public BusRouteInfoDto searchBusRouteInfo(Long sequence) {
        BusRouteInfo busRouteInfosBySequence = busRouteInfoRepo.findBusRouteInfosBySequence(sequence);
        return toDtoConvert.busRouteInfoEntityToDto(busRouteInfosBySequence);
    }

    @Override
    public List<BusStopRouteInfoDto> reqBusStopRouteInfoApi(String secretkey,String routeId) {
        //TODO: 해당 API 에선 노선 ID 는 제공하는데, 노선 번호는 제공하지 않음(EX: 733,753..)
        String pageNo = "1";
        String totalCount = "50";

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


}