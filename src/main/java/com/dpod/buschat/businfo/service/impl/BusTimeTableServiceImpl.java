package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BusTimeTableServiceImpl implements BusTimeTableService {

    private final BusInfoApiService busInfoApiService;

    private final BusRouteInfoRepo busRouteInfoRepo;

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
        List<String> busTimeTalbeList = Arrays.asList(busRouteInfosByBrtId.getBrtTimeTable().split(","));



        return List.of();
    }


    @Transactional
    @Override
    public void saveTimeTableInfo() {
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
            String timeTableListToStr = timeTableList.toString().replace("[", "").replace("]", "");
            busRouteInfoBySequence.updateTimeTable(timeTableListToStr);
        }
    }
}
