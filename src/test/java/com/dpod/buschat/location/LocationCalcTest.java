package com.dpod.buschat.location;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@Slf4j
public class LocationCalcTest {

        /// main : 기준 좌표 , compare : 기준 좌표와 비교할 좌표
        private static final double mainLat = 35.57426757;

        private static final double mainLon = 129.2416312;

        private static final double compareLat = 35.57314942;

        private static final double compareLon = 129.2419171;

        private static final double EARTH_RADIUS = 6371000;

    @Test
    @DisplayName("하버사인 공식으로 두 좌표 간 거리 계산")
    void calculateDistanceWithHaversine() {
        // 1. 위도/경도 라디안으로 변환
        double lat1Rad = Math.toRadians(mainLat);
        double lon1Rad = Math.toRadians(mainLon);
        double lat2Rad = Math.toRadians(compareLat);
        double lon2Rad = Math.toRadians(compareLon);

        // 2. 라디안 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // 3. 하버사인 공식 적용
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);

        // 4. 중심각 구하기
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 5. 최종 거리 구하기
        double distance = EARTH_RADIUS * c;

        log.info("두 좌표 사이 거리: {} m", BigDecimal.valueOf(distance));

        /// pow : 거듭제곱 , sin : 사인 값 , cos : 코사인 값 , atan2 : 각도(기울기)를 방향까지 고려해서 정확하게 계산하는 함수 , sqrt : 제곱근
    }


    @Test
    @DisplayName("입력 좌표에서 500미터 차이 구하기")
    void findBusStopNear500Meters() {
        double latDiff  = 500/111000.0;
        double lonDiff  = 500/(111000.0 * Math.cos(Math.toRadians(mainLat)));

        log.info("{},{}",latDiff,lonDiff);
    }

}

