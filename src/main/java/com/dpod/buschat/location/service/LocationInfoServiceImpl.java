package com.dpod.buschat.location.service;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.dto.LatLonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocationInfoServiceImpl implements LocationInfoService {

    private static final double EARTH_RADIUS = 6371000;

    @Override
    public List<BusStopInfoDto> searchNearBusStopInfo(double latitude, double longitude) {
        return List.of();
    }


    @Override
    public List<Pair<String,Double>> calculateDistance(LatLonDto userLocationDto, List<BusStopInfoDto> busStopInfoDtoList) {
        /**
         * 위도/경도 좌표를 비교해서, 두 위치간 거리를 구하는 로직
         * 하버사인 공식이 적용되었으며, 해당 공식 사용을 위해 라디안 형식으로 변환
         * **/
        double userLatToRadians = Math.toRadians(userLocationDto.getLatitude());
        double userLonToRadians = Math.toRadians(userLocationDto.getLongitude());

        List<Pair<String,Double>> calcDistanceList = new ArrayList<>();

        for(BusStopInfoDto busStopInfoDto : busStopInfoDtoList) {
            double busStopLatToRadians = Math.toRadians(busStopInfoDto.getBusStopLat());
            double busStopLonToRadians = Math.toRadians(busStopInfoDto.getBusStopLon());

            double deltaLat = busStopLatToRadians - userLatToRadians;
            double deltaLon = busStopLonToRadians - userLonToRadians;

            /// 하버사인 공식 적용
            double haversineFormula = Math.pow(Math.sin(deltaLat / 2), 2)
                    + Math.cos(userLatToRadians) * Math.cos(busStopLatToRadians) * Math.pow(Math.sin(deltaLon / 2), 2);

            /// 중심각 계산
            double centerAngle = 2 * Math.atan2(Math.sqrt(haversineFormula), Math.sqrt(1 - haversineFormula));

            /// 최종 거리 계산
            double resultDistance = EARTH_RADIUS * centerAngle;

            Pair<String,Double> calcDistanceResult = Pair.of(busStopInfoDto.getBusStopId(), resultDistance); ///< 정류장 아이디 , 사용자 위치와 정류장 위치의 거리 차이> pair 구조로 저장
            calcDistanceList.add(calcDistanceResult);

            log.info("사용자 위치와 정류장 위치의 거리 차이 {} , 정류장 이름 : {}", resultDistance,busStopInfoDto.getBusStopName());
        }
        return calcDistanceList;
    }
}
