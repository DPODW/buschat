package com.dpod.buschat.businfo.service.impl;

import com.dpod.buschat.businfo.dto.BusTimeTableInfoDto;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BusTimeTableServiceImpl implements BusTimeTableService {


    @Override
    public List<BusTimeTableInfoDto> deleteAnotherDir(List<BusTimeTableInfoDto> busTimeTableInfoDtoList,String busRouteName) {
        //노선 번호는 같으나, 방면 정보가 다른 노선을 제거하는 메소드
        List<BusTimeTableInfoDto> deleteAnotherDirList = new ArrayList<>();

        for(BusTimeTableInfoDto busTimeTableInfoDto : busTimeTableInfoDtoList){
            if(busRouteName.equals(busTimeTableInfoDto.getBusRouteName())){
                deleteAnotherDirList.add(busTimeTableInfoDto);
            }
        }
        return deleteBeforeTimeTable(deleteAnotherDirList);
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
    
}
