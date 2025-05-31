package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.*;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.exception.BusInfoException;
import com.dpod.buschat.businfo.exception.ErrorCode;
import com.dpod.buschat.businfo.repo.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.BusStopRouteRepo;
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

    private final BusInfoSearchService busInfoSearchService;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusStopRouteRepo busStopRouteRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final ToEntityConvert toEntityConvert;

    public BusStopInfoServiceImpl(BusInfoApiService busInfoApiService, BusInfoSearchService busInfoSearchService, BusStopInfoRepo busStopInfoRepo, BusStopRouteRepo busStopRouteRepo, BusTimeTableService busTimeTableService, BusRouteInfoRepo busRouteInfoRepo, ToEntityConvert toEntityConvert) {
        this.busInfoApiService = busInfoApiService;
        this.busInfoSearchService = busInfoSearchService;
        this.busStopInfoRepo = busStopInfoRepo;
        this.busStopRouteRepo = busStopRouteRepo;
        this.busTimeTableService = busTimeTableService;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.toEntityConvert = toEntityConvert;
    }

    @Override
    public void saveBusStopInfo() {
        if(busStopInfoRepo.countAllBy()==0){
            log.info("버스 정류장 정보 저장 시작. . .");
            busStopInfoRepo.saveAll(createBusStopInfoEntity());
        }else {
            log.info("버스 정류장 정보 업데이트 시작. . .");
            busStopInfoRepo.deleteAllInBatch();
            busStopInfoRepo.resetBusStopInfoSequence();
            busStopInfoRepo.saveAll(createBusStopInfoEntity());
        }
        busStopInfoRepo.save(toEntityConvert.busStopDtoToEntity(cityBusInfoDto()));
        /// 시티버스 정류장 정보는 API 에서 제공하지 않기 때문에, 수동으로 저장.
    }

    @Override
    public void saveBusStopRouteInfo() {
        List<BusStopInfoDto> busStopInfoDtoList = busInfoApiService.requestBusStopInfo();
        int busStopTotalApi = Integer.parseInt(busStopInfoDtoList.get(busStopInfoDtoList.size() - 1).getRNum())+1;
        //시티버스 정류장 정보는 API 에서 제공 하지 않기 때문에, 시티버스 정류장 정보 1건을 API 결과에 더해줌

        List<BusRouteInfoDto> busRouteInfoDtoList = busInfoApiService.requestBusRouteInfo();
        int busRouteTotalApi = Integer.parseInt(busRouteInfoDtoList.get(busRouteInfoDtoList.size() - 1).getRNum());
        int busRouteTotalDB = busRouteInfoRepo.countAllBy();

        if(busStopTotalApi!=busStopInfoRepo.countAllBy()){
            log.error("API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다름. API 제공 정류장 개수= {} / DB 저장 정류장 개수 = {}", busStopTotalApi,busStopInfoRepo.countAllBy());
            throw new BusInfoException(ErrorCode.BUSSTOP_COUNT_MISMATCH);
        }

        if(busRouteTotalApi!=busRouteTotalDB) {
            log.error("API 제공 노선 개수와 DB에 저장된 노선 개수가 다릅니다 API 제공 노선 개수= {} / DB 저장 노선 개수 = {}", busRouteTotalApi,busRouteTotalDB);
            throw new BusInfoException(ErrorCode.ROUTE_COUNT_MISMATCH);
        }

        if(busStopInfoRepo.countByBusStopRouteIdListIsNotNull()==0){
            log.info("정류장 정차 노선 정보 저장 시작. . .");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busStopRouteRepo.saveBusStopRoute(busRouteRoadInfoDto);
                }
            }
        }else{
            log.info("정류장 정차 노선 정보 업데이트 시작. . .");
            for (int i = 1; i <=busRouteTotalApi; i++) {
                List<BusRouteRoadInfoDto> busRouteRoadInfoDtoList = getBusRouteRoadInfoList(i);
                for(BusRouteRoadInfoDto busRouteRoadInfoDto : busRouteRoadInfoDtoList){
                    busStopRouteRepo.updateBusStopRoute(busRouteRoadInfoDto);
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
                busArrivalAllList.add(getInactiveBusInfo(busStopRouteInfoDto));
            }
        }
        busArrivalInfoDtoList.addAll(busArrivalAllList);
        return busArrivalInfoDtoList;
    }

    private BusArrivalInfoDto getInactiveBusInfo(BusStopRouteInfoDto busStopRouteInfoDto) {
        return BusArrivalInfoDto.builder()
                .busRouteNm(busStopRouteInfoDto.getBrtName())
                .busRouteId(busStopRouteInfoDto.getBrtId())
                .busStartTime(
                        busTimeTableService.getUpcomingTimeTable(busTimeTableService.getAvailableTimeTable(busStopRouteInfoDto.getBrtId()))
                )
                .busStopStName(busStopRouteInfoDto.getBusStopStName())
                .build();
    }

    @Override
    public List<BusArrivalInfoDto> createInactiveBusList(List<BusStopRouteInfoDto> busStopRouteInfoList) {
        ArrayList<BusArrivalInfoDto> busInactiveList = new ArrayList<>();
        for(BusStopRouteInfoDto busStopRouteInfoDto : busStopRouteInfoList){
            busInactiveList.add(getInactiveBusInfo(busStopRouteInfoDto));
        }
        return busInactiveList;
    }

    private List<BusRouteRoadInfoDto> getBusRouteRoadInfoList(long i) {
        BusRouteInfoDto busRouteInfoDto = busInfoSearchService.searchBusRouteInfo(i);
        return busInfoApiService.requestBusStopRouteInfo(busRouteInfoDto.getBrtId());
    }

    private static BusStopInfoDto cityBusInfoDto() {
        return BusStopInfoDto.builder()
                .busStopId("999000020")
                .busStopName("태화강역(시티버스 정류소)")
                .busStopLon(129.3529295)
                .busStopLat(35.5389133)
                .busStopMark("울산 시티버스 정류장").build();
    }
}
