package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.BusInfoRepository;
import com.dpod.buschat.businfo.service.BusInfoService;
import com.dpod.buschat.businfo.vo.BusStopInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class BusInfoServiceImpl implements BusInfoService {

    private final BusInfoRepository busInfoRepository;

    public BusInfoServiceImpl(BusInfoRepository busInfoRepository) {
        this.busInfoRepository = busInfoRepository;
    }

    @Override
    public void saveBusStopInfo(List<BusStopInfoDto> busStopInfoDtoList) {
        busStopInfoDtoList.stream().forEach(
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = busStopDtoToEntity(busStopInfoDto);
                    busInfoRepository.save(busStopInfo);
                });
    }

    @Override
    public void searchBusStopInfo(String busStopName) {
        List<BusStopInfo> allInfoByBusStopName = busInfoRepository.findAllByBusStopName(busStopName);

        log.info("{}",allInfoByBusStopName);
    }


    private BusStopInfo busStopDtoToEntity(BusStopInfoDto busStopInfoDto){
        BusStopInfo busStopInfo =  BusStopInfo.builder()
                .busStopId(busStopInfoDto.getBusStopId())
                .busStopName(busStopInfoDto.getBusStopName())
                .busStopX(busStopInfoDto.getBusStopX())
                .busStopY(busStopInfoDto.getBusStopY())
                .busStopMark(busStopInfoDto.getBusStopMark())
                .build();
        return busStopInfo;
    }




}
