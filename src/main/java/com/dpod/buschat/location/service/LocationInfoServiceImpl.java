package com.dpod.buschat.location.service;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.BusStopLocationRepo;
import com.dpod.buschat.businfo.service.impl.ToDtoConvert;
import com.dpod.buschat.businfo.service.impl.ToEntityConvert;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import com.dpod.buschat.location.exception.LocationErrorCode;
import com.dpod.buschat.location.exception.LocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocationInfoServiceImpl implements LocationInfoService {

    private static final double EARTH_RADIUS = 6371000;

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusStopLocationRepo busStopLocationRepo;

    private final ToDtoConvert toDtoConvert;

    public LocationInfoServiceImpl(BusStopInfoRepo busStopInfoRepo, BusStopLocationRepo busStopLocationRepo, ToDtoConvert toDtoConvert) {
        this.busStopInfoRepo = busStopInfoRepo;
        this.busStopLocationRepo = busStopLocationRepo;
        this.toDtoConvert = toDtoConvert;
    }

    @Override
    public BusStopInfoDto getUserNearBusStopResult(double userLatitude, double userLongitude) {
        LatLonDto userLatLonDto = LatLonDto.builder()
                .latitude(userLatitude)
                .longitude(userLongitude).build();

        List<BusStopInfoDto> range500mBusStopInfoList = calculate500mRangeWithLatLon(userLatLonDto);
        List<Pair<String, Double>> calcDistanceList = calculateDistance(userLatLonDto, range500mBusStopInfoList);
        Pair<String, Double> shortDistanceBusStop = getShortDistanceBusStop(calcDistanceList);
        BusStopInfoDto range50mBusStopInfo = get50mRangeBusStop(shortDistanceBusStop);
        log.info("입력된 좌표와 제일 가까운 정류장 : *{}* , 정류장 방면 정보 : *{}*",range50mBusStopInfo.getBusStopName(),range50mBusStopInfo.getBusStopMark());
        return range50mBusStopInfo;
    }

    @Override
    public List<BusStopInfoDto> calculate500mRangeWithLatLon(LatLonDto userLocation) {
        /**
         * 주어진 위도/경도 상 500m 범위 (500m에 대응하는 각도) 를 계산, 500m 안에 있는 정류장 LIST 반환
         * **/
        double lat500mValue = 500/111000.0; /// 위도상 500m
        double lon500mValue = 500/(111000.0 * Math.cos(Math.toRadians(userLocation.getLatitude()))); ///경도상 500m

        RangeLatLonDto rangeLatLonDto = RangeLatLonDto.builder()
                .minLatitude(userLocation.getLatitude() - lat500mValue)
                .maxLatitude(userLocation.getLatitude() + lat500mValue)
                .minLongitude(userLocation.getLongitude() - lon500mValue)
                .maxLongitude(userLocation.getLongitude() + lon500mValue)
                .build();

        List<BusStopInfo> range500mBusStopInfoList = busStopLocationRepo.searchNear500MBusStop(rangeLatLonDto);
        if(range500mBusStopInfoList.isEmpty()) {
            throw new LocationException(LocationErrorCode.BUSSTOP_NOT_FOUND_500M_RANGE);
        }

        List<BusStopInfoDto> range500mBusStopInfoDtoList = new ArrayList<>();
        for(BusStopInfo range500mBusStopInfo : range500mBusStopInfoList) {
            range500mBusStopInfoDtoList.add(toDtoConvert.busStopEntityToDto(range500mBusStopInfo));
            log.info("위치로부터 500m 범위 안에 있는 정류장 정보 : *{}* , 방면 : *{}*", range500mBusStopInfo.getBusStopName(),range500mBusStopInfo.getBusStopMark());
        }

        return range500mBusStopInfoDtoList;
    }


    @Override
    public List<Pair<String,Double>> calculateDistance(LatLonDto userLocationDto, List<BusStopInfoDto> busStopInfoDtoList) {
        /**
         * 위도/경도 좌표를 비교해서, 두 위치간 거리를 구하는 로직
         * 하버사인 공식이 적용되었으며, 해당 공식 사용을 위해 라디안 형식으로 변환
         * 최종적으로 정류장 ID - 위치간 거리 차이 를 Pair 에 넣어서 LIST 로 반환
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

        }
        return calcDistanceList;
    }

    @Override
    public Pair<String,Double> getShortDistanceBusStop(List<Pair<String, Double>> user500mRangeWithLatLon) {
        /// PAIR <버스 ID , 거리 차이> , 해당 List 에서 제일 거리 차이가 작은 PAIR 를 반환한다.
        Pair<String,Double> shortDistanceBusStop = null;
        double minDistance = Double.MAX_VALUE;

        for(int i = 0; i<user500mRangeWithLatLon.size(); i++) {
            if(user500mRangeWithLatLon.get(i).getSecond() < minDistance) {
                minDistance = user500mRangeWithLatLon.get(i).getSecond();
                shortDistanceBusStop = user500mRangeWithLatLon.get(i);
            }
        }
       return shortDistanceBusStop;
    }

    @Override
    public BusStopInfoDto get50mRangeBusStop(Pair<String,Double> shortDistanceBusStop ) {
        /// 해당 정류장이 50미터 이내에 있는지 검증한다. (현재 테스트를 위해 400미터로 거리 확장)
        if(shortDistanceBusStop.getSecond() <= 400.0){
            BusStopInfo shortDistanceBusStopInfo = busStopInfoRepo.findAllByBusStopId(shortDistanceBusStop.getFirst());
            return toDtoConvert.busStopEntityToDto(shortDistanceBusStopInfo);
        }else
            throw new LocationException(LocationErrorCode.BUSSTOP_NOT_FOUND_50M_RANGE);
    }

}
