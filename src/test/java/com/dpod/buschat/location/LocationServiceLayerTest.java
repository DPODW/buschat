package com.dpod.buschat.location;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import com.dpod.buschat.location.service.LocationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class LocationServiceLayerTest {

    private final LocationInfoService locationInfoService;

    @Autowired
    public LocationServiceLayerTest(LocationInfoService locationInfoService) {
        this.locationInfoService = locationInfoService;
    }

    @Test
    @DisplayName("위도/경도 상 500m (각도상 500m) 거리 구하는 테트스 (calculate500mRangeWithLatLon 메소드 테스트)")
    public void testCalculate500mRangeWithLatLon(){
        LatLonDto testLatLonDto = LatLonDto.builder()
                .latitude(35.32639629)
                .longitude(129.276875)
                .build();

        List<BusStopInfoDto> busStopInfoDtos = locationInfoService.calculate500mRangeWithLatLon(testLatLonDto);
        log.info("각도상 500m : {}",busStopInfoDtos);
    }



    @Test
    @DisplayName("두 좌표간 거리 구하는 테스트 (calculateDistance 메소드 테스트)")
    public void testCalculateDistance() {
        LatLonDto latLonDto = LatLonDto.builder()
                .longitude(129.2410323)
                .latitude(35.57528453)
                .build();

        List<BusStopInfoDto> busStopInfoDtoList = new ArrayList<>();

        /// dummy data
        BusStopInfoDto busStopInfoDtoTest = BusStopInfoDto.builder()
                .busStopName("우미린2차 푸르지오2차 (범서 파출소 방면)")
                .busStopId("196020415")
                .busStopLon(129.2419171)
                .busStopLat(35.57314942)
                .build();

        BusStopInfoDto busStopInfoDtoTest1 = BusStopInfoDto.builder()
                .busStopName("우미린2차 푸르지오2차 (우미린 1차 방면)")
                .busStopId("196020416")
                .busStopLon(129.2416312)
                .busStopLat(35.57426757)
                .build();

        BusStopInfoDto busStopInfoDtoTest2 = BusStopInfoDto.builder()
                .busStopName("우미린1차아파트 (굿모닝 힐 방면)")
                .busStopId("196020422")
                .busStopLon(129.2408219)
                .busStopLat(35.57624471)
                .build();

        BusStopInfoDto busStopInfoDtoTest3 = BusStopInfoDto.builder()
                .busStopName("범서중학교앞 (대리 방면)")
                .busStopId("196020411")
                .busStopLon(129.2438043)
                .busStopLat(35.5683983)
                .build();

        busStopInfoDtoList.add(busStopInfoDtoTest);
        busStopInfoDtoList.add(busStopInfoDtoTest1);
        busStopInfoDtoList.add(busStopInfoDtoTest2);
        busStopInfoDtoList.add(busStopInfoDtoTest3);


        locationInfoService.calculateDistance(latLonDto, busStopInfoDtoList);
    }

    @Test
    @DisplayName("제일 거리가 가까운 데이터 가져오기")
    public void testShortDistanceBusStop() {
        List<Pair<String, Double>> testUser500mRangeWithLatLon = new ArrayList<>();

        Pair<String, Double> testData1 = Pair.of("196020415",250.53699816171576);
        Pair<String, Double> testData2 = Pair.of("196020416",125.38395499423416);
        Pair<String, Double> testData3 = Pair.of("196020422",108.44957488591103);
        Pair<String, Double> testData4 = Pair.of("196020411",805.7134980578568);

        testUser500mRangeWithLatLon.add(testData1);
        testUser500mRangeWithLatLon.add(testData2);
        testUser500mRangeWithLatLon.add(testData3);
        testUser500mRangeWithLatLon.add(testData4);

        locationInfoService.getShortDistanceBusStop(testUser500mRangeWithLatLon);
    }
}
