package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BusTimeTableServiceImpl implements BusTimeTableService {

    private final BusInfoApiService busInfoApiService;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final static String NO_BUS_INFO = "운행 정보 없음";

    private final static String NO_TIMETABLE_INFO = "시간표 정보 없음";

    public BusTimeTableServiceImpl(BusInfoApiService busInfoApiService, BusRouteInfoRepo busRouteInfoRepo) {
        this.busInfoApiService = busInfoApiService;
        this.busRouteInfoRepo = busRouteInfoRepo;
    }


    @Override
    public List<BusTimeTableInfoDto> deleteAnotherDir(List<BusTimeTableInfoDto> busTimeTableInfoDtoList,String busRouteName) {
        //노선 번호는 같으나, 방면 정보가 다른 노선을 제거하는 메소드
        List<BusTimeTableInfoDto> deleteAnotherDirList = new ArrayList<>();

        for(BusTimeTableInfoDto busTimeTableInfoDto : busTimeTableInfoDtoList){
            if(busRouteName.equals(busTimeTableInfoDto.getBusRouteName())){
                deleteAnotherDirList.add(busTimeTableInfoDto);
            }
        }
        return deleteAnotherDirList;
    }

    @Override
    public List<BusTimeTableInfoDto> deleteAnotherClass(List<BusTimeTableInfoDto> busTimeTableInfoDtoList, String busRouteClass) {
        //클래스 정보가 다른 노선을 제거하는 메소드
        List<BusTimeTableInfoDto> deleteAnotherClassList = new ArrayList<>();

        for(BusTimeTableInfoDto busTimeTableInfoDto : busTimeTableInfoDtoList){
            if(busTimeTableInfoDto.getBusRouteClass().equals(busRouteClass)){
                deleteAnotherClassList.add(busTimeTableInfoDto);
            }
        }
        return deleteAnotherClassList;
    }

    @Override
    public List<String> getAvailableTimeTable(String busRouteId) {
        BusRouteInfo busRouteInfosByBrtId = busRouteInfoRepo.findBusRouteInfosByBrtId(busRouteId);
        if(busRouteInfosByBrtId.getBrtTimeTable()==null){
            return List.of(NO_TIMETABLE_INFO);
        } else
            return Arrays.asList(busRouteInfosByBrtId.getBrtTimeTable().split(","));
    }

    @Override
    public String getUpcomingTimeTable(List<String> busTimeTableList) {
        if(busTimeTableList.get(0).equals(NO_TIMETABLE_INFO)){
            return NO_BUS_INFO;
        }

        LocalTime now = LocalTime.now();
        List<LocalTime> localTimeTypeList = new ArrayList<>();
        for (String time : busTimeTableList) {
            localTimeTypeList.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm")));
        }

        for (LocalTime localTime : localTimeTypeList) {
            if (localTime.isAfter(now)) {
                return localTime.toString();

            } else if(localTimeTypeList.get(localTimeTypeList.size() - 1).isBefore(now)) {
                return localTime.toString();
            }
        }
        return NO_BUS_INFO;
    }


    @Transactional
    @Override
    public void saveTimeTableInfo() {
        if(busRouteInfoRepo.countAllBy()>=1){
            log.info("기존 시간표 데이터 삭제");
            busRouteInfoRepo.updateTimeTableNull();
        }

        log.info("버스 시간표 저장 시작");
        for(int i=1; i<=busRouteInfoRepo.countAllBy(); i++){
            List<String> timeTableList = new ArrayList<>();

            BusRouteInfo busRouteInfoBySequence = busRouteInfoRepo.findBusRouteInfosBySequence((long)i);
            List<BusTimeTableInfoDto> busTimeTableInfoDtoList = busInfoApiService.requestBusTimeTableInfo(busRouteInfoBySequence.getBrtNo());

            Optional.ofNullable(busTimeTableInfoDtoList)
                    .map(busTimeTableList -> deleteAnotherDir(busTimeTableList, busRouteInfoBySequence.getBrtName()))
                    .map(deleteAnotherDirList -> deleteAnotherClass(deleteAnotherDirList, busRouteInfoBySequence.getBusClass()))
                    .ifPresent(deleteAnotherClassList ->
                            deleteAnotherClassList.forEach(deleteAnotherClass -> timeTableList.add(deleteAnotherClass.getBusStTime()))
                    );
            String timeTableListToStr = timeTableList.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
            busRouteInfoBySequence.updateTimeTable(timeTableListToStr);
        }
    }
}
