package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.service.BusInfoApiService;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
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
        List<BusTimeTableInfoDto> deleteAnotherClassList = new ArrayList<>();

        for(BusTimeTableInfoDto busTimeTableInfoDto : busTimeTableInfoDtoList){
            if(busTimeTableInfoDto.getBusRouteClass().equals(busRouteClass)){
                deleteAnotherClassList.add(busTimeTableInfoDto);
            }
        }
        return deleteAnotherClassList;
    }


    @Override
    public List<BusTimeTableInfoDto> deleteBeforeTimeTable(List<BusTimeTableInfoDto> busTimeTableInfoDtoList) {
        //현재시간보다 이전 시간의 출발 시간을 제거하는 메소드
        List<BusTimeTableInfoDto> deleteBeforeTimeTableList = new ArrayList<>();

        LocalTime now = LocalTime.now();
        String hour = String.valueOf(now.getHour());
        String minute = String.valueOf(now.getMinute());

        if(now.getHour() < 10 && now.getMinute() < 10) {
            hour = "0" + hour;
            minute = "0" + minute;
        }

        for(BusTimeTableInfoDto busTimeTableInfoDto : busTimeTableInfoDtoList){
            if(Integer.parseInt(busTimeTableInfoDto.getBusStTime()) > Integer.parseInt(hour.concat(minute))){
                deleteBeforeTimeTableList.add(busTimeTableInfoDto);
            }
        }

        //LIST 가 비어있을시, 금일 모든 버스 출발 계획이 없는것이므로, 모든 시간표 정보를 반환함(내일 버스는 한대도 출발안했으니까)
        if(deleteBeforeTimeTableList.isEmpty()){
            return busTimeTableInfoDtoList;
        }else
            return deleteBeforeTimeTableList;
    }

    @Override
    public List<BusTimeTableInfoDto> getBusTimeTableInfo(String busRouteId) {
            BusRouteInfo busRouteInfoByBrtId = busRouteInfoRepo.findBusRouteInfosByBrtId(busRouteId);
            List<BusTimeTableInfoDto> busTimeTableInfoDtoList = busInfoApiService.requestBusTimeTableInfo(busRouteInfoByBrtId.getBrtNo());
            //List<BusTimeTableInfoDto> deleteAnotherDirList = deleteAnotherDir(busTimeTableInfoDtoList, busRouteInfoByBrtId.getBrtName());
            //List<BusTimeTableInfoDto> deleteAnotherClassList = deleteAnotherClass(busTimeTableInfoDtoList, busRouteInfoByBrtId.getBusClass());
            //List<BusTimeTableInfoDto> deleteBeforeTimeTableList = deleteBeforeTimeTable(deleteAnotherClassList);

        return busTimeTableInfoDtoList;
    }


    @Override
    public String formatRouteNmForApi(String busRouteName) {
        int stringIdx = busRouteName.indexOf("(");
        return busRouteName.substring(0, stringIdx);
    }

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
            log.info("시간표 {}",timeTableList);
        }
    }


//    @Override
//    public List<BusTimeTableInfoDto> getBusTimeTableInfo(String busRouteName) {
//        /// 테스트를 위해 일시적으로 deleteAnotherDirList , deleteAnotherDirList 메소드 주석 처리
//        if(!busRouteName.matches("^[0-9].*")){
//            BusRouteInfo villageBusRouteInfo = busRouteInfoRepo.findBusRouteInfoByBrtName(busRouteName);
//            List<BusTimeTableInfoDto> villageBusTimeTableList = busInfoApiService.requestBusTimeTableInfo(villageBusRouteInfo.getBrtNo());
////            List<BusTimeTableInfoDto> deleteAnotherDirList = deleteAnotherDir(villageBusTimeTableList, busRouteName);
////            return deleteBeforeTimeTable(deleteAnotherDirList);
//            return villageBusTimeTableList;
//        }else{
//            List<BusTimeTableInfoDto> reqBusTimeTableList = busInfoApiService.requestBusTimeTableInfo(formatRouteNmForApi(busRouteName));
////            List<BusTimeTableInfoDto> deleteAnotherDirList = deleteAnotherDir(reqBusTimeTableList,busRouteName);
////            return deleteBeforeTimeTable(deleteAnotherDirList);
//            return reqBusTimeTableList;
//        }
//    }

    @Override
    public void checkOtherRouteName() {
        for(int i=1; i<=busRouteInfoRepo.countAllBy(); i++){
            BusRouteInfo busRouteInfoBySequence = busRouteInfoRepo.findBusRouteInfosBySequence((long)i);
            //List<BusTimeTableInfoDto> busTimeTableInfoDtoList = requestBusTimeTableInfo(busRouteInfoBySequence.getBrtName());

            List<BusTimeTableInfoDto> busTimeTableInfoDtoList = getBusTimeTableInfo(busRouteInfoBySequence.getBrtId());

            if(busTimeTableInfoDtoList==null){
                log.info("getBrtName : {}",busRouteInfoBySequence.getBrtName());
                log.info("반복횟수 {}",i);
            }

            if(busTimeTableInfoDtoList!=null && deleteAnotherClass(busTimeTableInfoDtoList,busRouteInfoBySequence.getBusClass()).isEmpty()){
                log.info("시간표에 없는 클래스 정보 : {}",busRouteInfoBySequence.getBrtName());
            }

            if(busTimeTableInfoDtoList!= null && deleteAnotherDir(busTimeTableInfoDtoList, busRouteInfoBySequence.getBrtName()).isEmpty()){
                log.info("시간표에 없는 방면 정보 : {}",busRouteInfoBySequence.getBrtName());
            }
        }
    }
}
