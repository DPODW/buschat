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
                    log.info(">>{}",busStopInfo);
                    busInfoRepository.save(busStopInfo);
                });
    }


    private BusStopInfo busStopDtoToEntity(BusStopInfoDto busStopInfoDto){
        BusStopInfo busStopInfo = new BusStopInfo();
        busStopInfo.setBusStopId(busStopInfoDto.getBusStopId());
        busStopInfo.setBusStopName(busStopInfoDto.getBusStopName());
        busStopInfo.setBusStopX(busStopInfoDto.getBusStopX());
        busStopInfo.setBusStopY(busStopInfoDto.getBusStopY());
        busStopInfo.setBusStopMark(busStopInfoDto.getBusStopMark());
        return busStopInfo;
    }




}
