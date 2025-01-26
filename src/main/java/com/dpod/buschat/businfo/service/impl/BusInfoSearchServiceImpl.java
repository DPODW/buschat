package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusRouteInfoDto;
import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusInfoRepository;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusInfoSearchServiceImpl implements BusInfoSearchService{

    private final BusInfoRepository busInfoRepository;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToDtoConvert toDtoConvert;

    public BusInfoSearchServiceImpl(BusInfoRepository busInfoRepository, BusRouteInfoRepo busRouteInfoRepo, ToDtoConvert toDtoConvert) {
        this.busInfoRepository = busInfoRepository;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.toDtoConvert = toDtoConvert;
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
}
