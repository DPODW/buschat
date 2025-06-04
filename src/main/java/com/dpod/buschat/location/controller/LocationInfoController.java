package com.dpod.buschat.location.controller;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.service.LocationInfoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bus")
public class LocationInfoController {

    private final LocationInfoService locationInfoService;

    public LocationInfoController(LocationInfoService locationInfoService) {
        this.locationInfoService = locationInfoService;
    }

    @GetMapping("/location/user/{latitude}/{longitude}")
    public BusStopInfoDto searchUserNearBusStop(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
        LatLonDto userLatLonDto = LatLonDto.builder()
                .latitude(latitude)
                .longitude(longitude).build();

        List<BusStopInfoDto> range500mBusStopInfoList = locationInfoService.calculate500mRangeWithLatLon(userLatLonDto);
        List<Pair<String, Double>> calcDistanceList = locationInfoService.calculateDistance(userLatLonDto, range500mBusStopInfoList);
        BusStopInfoDto shortDistanceBusStop = locationInfoService.getShortDistanceBusStop(calcDistanceList);
        log.info("입력된 좌표와 제일 가까운 정류장 : *{}* , 정류장 방면 정보 : *{}*",shortDistanceBusStop.getBusStopName(),shortDistanceBusStop.getBusStopMark());
        return shortDistanceBusStop;
    }
}
