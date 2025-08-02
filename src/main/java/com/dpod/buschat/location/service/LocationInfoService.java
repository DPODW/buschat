package com.dpod.buschat.location.service;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationInfoService {

    BusStopInfoDto getUserNearBusStopResult(double userLatitude, double userLongitude);

    List<BusStopInfoDto> calculate500mRangeWithLatLon(LatLonDto userLocation);

    List<Pair<String,Double>> calculateDistance(LatLonDto busStopLocation, List<BusStopInfoDto> busStopInfoDto);

    Pair<String,Double> getShortDistanceBusStop(List<Pair<String,Double>> user500mRangeWithLatLon);

    BusStopInfoDto get50mRangeBusStop(Pair<String,Double> shortDistanceBusStop);
}
