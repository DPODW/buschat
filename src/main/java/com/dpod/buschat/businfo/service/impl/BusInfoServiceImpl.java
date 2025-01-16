package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusInfoRepository;
import com.dpod.buschat.businfo.service.BusInfoService;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
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
        busStopInfoDtoList.forEach( //foreach : stream 생략 가능
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = busStopDtoToEntity(busStopInfoDto);
                    busInfoRepository.save(busStopInfo);
                });
    }

    @Override
    public List<BusStopInfoDto> searchBusStopInfo(String busStopName) {
        List<BusStopInfo> allInfoByBusStopName = busInfoRepository.findAllByBusStopNameLike("%"+busStopName+"%");
        
        List<BusStopInfoDto> busStopInfoDtoList = allInfoByBusStopName.stream()
                .map(busStopInfo -> {
                            return busStopEntityToDto(busStopInfo);
                        }
                ).toList();
        log.info("{}",busStopInfoDtoList);
        return busStopInfoDtoList;
    }


    private BusStopInfo busStopDtoToEntity(BusStopInfoDto busStopInfoDto){
        return BusStopInfo.builder()
                .busStopId(busStopInfoDto.getBusStopId())
                .busStopName(busStopInfoDto.getBusStopName())
                .busStopX(busStopInfoDto.getBusStopX())
                .busStopY(busStopInfoDto.getBusStopY())
                .busStopMark(busStopInfoDto.getBusStopMark())
                .build();
    }



    private BusStopInfoDto busStopEntityToDto(BusStopInfo busStopInfo){
        return BusStopInfoDto.builder()
                .busStopId(busStopInfo.getBusStopId())
                .busStopName(busStopInfo.getBusStopName())
                .busStopX(busStopInfo.getBusStopX())
                .busStopY(busStopInfo.getBusStopY())
                .busStopMark(busStopInfo.getBusStopMark())
                .build();
    }
}