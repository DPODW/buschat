package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.*;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.exception.bus.BusInfoException;
import com.dpod.buschat.businfo.exception.bus.ErrorCode;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class BusStopInfoServiceImpl implements BusStopInfoService {

    private final BusInfoApiService busInfoApiService;

    private final BusTimeTableService busTimeTableService;

    private final BusRouteInfoService busRouteInfoService;

    private final BusInfoSearchService busInfoSearchService;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusStopRouteRepo busStopRouteRepo;

    private final ToEntityConvert toEntityConvert;

    public BusStopInfoServiceImpl(BusInfoApiService busInfoApiService, BusRouteInfoService busRouteInfoService, BusInfoSearchService busInfoSearchService, BusStopInfoRepo busStopInfoRepo, BusStopRouteRepo busStopRouteRepo, BusTimeTableService busTimeTableService, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoService = busRouteInfoService;
        this.busInfoSearchService = busInfoSearchService;
        this.busStopInfoRepo = busStopInfoRepo;
        this.busStopRouteRepo = busStopRouteRepo;
        this.busTimeTableService = busTimeTableService;
        this.toEntityConvert = toEntityConvert;
    }

    @Override
    public void saveBusStopInfo() {
        busStopInfoRepo.saveAll(createBusStopInfoEntity());
    }

    @Override
    public void updateBusStopInfo() {
        busStopInfoRepo.deleteAllInBatch();
        busStopInfoRepo.resetBusStopInfoSequence();
        busStopInfoRepo.saveAll(createBusStopInfoEntity());
    }

    @Override
    public void saveBusStopRouteInfo() {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo();
        int busStopTotalApi = Integer.parseInt(busStopInfoDtoList.get(busStopInfoDtoList.size() - 1).getRNum())+1;
        //시티버스 정류장 정보는 API 에서 제공 하지 않기 때문에, 시티버스 정류장 정보 1건을 API 결과에 더해줌

        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo();
        int busRouteTotalApi = Integer.parseInt(busRouteInfoDtoList.get(busRouteInfoDtoList.size() - 1).getRNum());
        int busRouteTotalDB = busRouteInfoService.countBusRouteInfo();

        if(busStopTotalApi!=countBusStopInfo()){
            log.error("API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다름. API 제공 정류장 개수= {} / DB 저장 정류장 개수 = {}", busStopTotalApi,countBusStopInfo());
            throw new BusInfoException(ErrorCode.BUSSTOP_COUNT_MISMATCH);
        }

        if(busRouteTotalApi!=busRouteTotalDB) {
            log.error("API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다 API 제공 노선 개수= {} / DB 저장 노선 개수 = {}", busRouteTotalApi,busRouteTotalDB);
            throw new BusInfoException(ErrorCode.ROUTE_COUNT_MISMATCH);
        }

        if(countBusStopRoute()==0){
            log.info("----------정류장 정차 노선 정보 저장 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    saveBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }else{
            log.info("----------정류장 정차 노선 정보 업데이트 시작----------");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    updateBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }
    }


    @Override
    public List<BusStopInfo> createBusStopInfoEntity() {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo();

        List<BusStopInfo> busStopInfoList = new ArrayList<>();

        busStopInfoDtoList.forEach(
                busStopInfoDto -> {
                    BusStopInfo busStopInfo = toEntityConvert.busStopDtoToEntity(busStopInfoDto);
                    busStopInfoList.add(busStopInfo);
                });
        return busStopInfoList;
    }

    @Override
    public List<BusArrivalInfoDto> createBusAllArrivalList(List<BusArrivalInfoDto> busArrivalInfoDtoList, List<BusStopRouteInfoDto> busStopRouteInfoList) {
        List<String> busStopRouteIdList = new ArrayList<>();
        List<BusArrivalInfoDto> busArrivalAllList = new ArrayList<>();

        for (BusArrivalInfoDto busArrivalInfoDto : busArrivalInfoDtoList) {
            busStopRouteIdList.add(busArrivalInfoDto.getBusRouteId());
        }

        for (BusStopRouteInfoDto busStopRouteInfoDto : busStopRouteInfoList) {
            if (busStopRouteIdList.contains(busStopRouteInfoDto.getBrtId())) {
                continue;
            } else {
                List<BusTimeTableInfoDto> busTimeTableInfoDtoList = busTimeTableService.deleteAnotherDir(busInfoApiService.requestBusTimeTableInfo(busStopRouteInfoDto.getBrtName()), busStopRouteInfoDto.getBrtName());

                BusArrivalInfoDto busInfo = BusArrivalInfoDto.builder()
                        .busRouteNm(busStopRouteInfoDto.getBrtName())
                        .busRouteId(busStopRouteInfoDto.getBrtId())
                        .busStartTime(busTimeTableInfoDtoList.get(0).getBusStTime()+" 출발 예정")
                        .busStopStName(busStopRouteInfoDto.getBusStopStName())
                        .build();

                busArrivalAllList.add(busInfo);
            }
        }
        busArrivalInfoDtoList.addAll(busArrivalAllList);
        return busArrivalInfoDtoList;
    }


    @Override
    public void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.saveBusStopRoute(busRouteRoadInfoDto);
    }

    @Override
    public void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        busStopRouteRepo.updateBusStopRoute(busRouteRoadInfoDto);
    }

    @Override
    public int countBusStopInfo() {
        return busStopInfoRepo.countAllBy();
    }

    @Override
    public int countBusStopRoute() {
        return busStopInfoRepo.countByBusStopRouteIdListIsNotNull();
    }

    private List<BusRouteRoadInfoDto> getBusRouteRoadInfoList(long i) {
        BusRouteInfoDto busRouteInfoDto = busInfoSearchService.searchBusRouteInfo(i);
        return busInfoApiService.requestBusStopRouteInfo(busRouteInfoDto.getBrtId());
    }
}
