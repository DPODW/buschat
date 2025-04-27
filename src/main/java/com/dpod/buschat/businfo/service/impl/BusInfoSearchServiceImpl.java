package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BusInfoSearchServiceImpl implements BusInfoSearchService{

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToDtoConvert toDtoConvert;

    public BusInfoSearchServiceImpl(BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo, ToDtoConvert toDtoConvert) {
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.toDtoConvert = toDtoConvert;
    }


    @Override
    public List<BusStopInfoDto> searchBusStopInfoToHgl(String busStopName) {
        //정류장 한글 이름으로 검색

        List<BusStopInfo> allInfoByBusStopName = busStopInfoRepo.findAllByBusStopNameLike("%"+busStopName+"%");

        return allInfoByBusStopName.stream()
                .map(busStopInfo -> {
                            return toDtoConvert.busStopEntityToDto(busStopInfo);
                        }
                ).toList();
    }

    @Override
    public BusStopInfoDto searchBusStopInfoToId(String busStopId) {
        //정류장 버스 정류장 아이디로 검색
        return toDtoConvert.busStopEntityToDto(busStopInfoRepo.findAllByBusStopId(busStopId));
    }

    @Override
    public BusRouteInfoDto  searchBusRouteInfo(Long sequence) {
        BusRouteInfo busRouteInfosBySequence = busRouteInfoRepo.findBusRouteInfosBySequence(sequence);
        return toDtoConvert.busRouteInfoEntityToDto(busRouteInfosBySequence);
    }

}
