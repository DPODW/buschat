package com.dpod.buschat.location.service;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.dto.LatLonDto;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationInfoService {

    List<BusStopInfoDto> searchNearBusStopInfo(double latitude, double longitude); ///latitude : 위도 , longitude : 경도


    List<Pair<String,Double>> calculateDistance(LatLonDto busStopLocation, List<BusStopInfoDto> busStopInfoDto);

}
