package com.dpod.buschat.location;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.service.LocationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .busStopLon(129.2419171)
                .busStopLat(35.57314942)
                .build();

        BusStopInfoDto busStopInfoDtoTest1 = BusStopInfoDto.builder()
                .busStopName("우미린2차 푸르지오2차 (우미린 1차 방면)")
                .busStopLon(129.2416312)
                .busStopLat(35.57426757)
                .build();

        BusStopInfoDto busStopInfoDtoTest2 = BusStopInfoDto.builder()
                .busStopName("우미린1차아파트 (굿모닝 힐 방면)")
                .busStopLon(129.2408219)
                .busStopLat(35.57624471)
                .build();

        BusStopInfoDto busStopInfoDtoTest3 = BusStopInfoDto.builder()
                .busStopName("범서중학교앞 (대리 방면)")
                .busStopLon(129.2438043)
                .busStopLat(35.5683983)
                .build();

        busStopInfoDtoList.add(busStopInfoDtoTest);
        busStopInfoDtoList.add(busStopInfoDtoTest1);
        busStopInfoDtoList.add(busStopInfoDtoTest2);
        busStopInfoDtoList.add(busStopInfoDtoTest3);


        locationInfoService.calculateDistance(latLonDto, busStopInfoDtoList);
    }
}
