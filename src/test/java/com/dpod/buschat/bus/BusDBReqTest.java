package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.BusStopLocationRepo;
import com.dpod.buschat.businfo.repo.impl.BusStopLocationRepoImpl;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import com.dpod.buschat.location.service.LocationInfoService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusDBReqTest {

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    private final BusStopLocationRepo busStopLocationRepo;

    private final LocationInfoService locationInfoService;

    @Autowired
    public BusDBReqTest(BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo, BusStopLocationRepo busStopLocationRepo, LocationInfoService locationInfoService) {
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo;
        this.busStopLocationRepo = busStopLocationRepo;
        this.locationInfoService = locationInfoService;
    }


    @Test
    @DisplayName("findAllByBusStopRouteIdListLike 메소드 테스트")
    void findAllByBusStopRouteIdListLikeTest() {
        String testBusRouteId = "%11111111%";

        List<BusStopInfo> allByBusStopRouteIdListLike = busStopInfoRepo.findAllByBusStopRouteIdListLike(testBusRouteId);

        assertThat(allByBusStopRouteIdListLike.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("BusRouteInfoRepo 의 CountAll 메소드 테스트")
    void countBusRouteInfo() {
        int busRouteInfoTotalCnt = busRouteInfoRepo.countAllBy();

        assertThat(busRouteInfoTotalCnt).isEqualTo(490);
    }

    @Test
    @DisplayName("BusStopInfoRepo 의 countByBusStopRouteIdListIsNotNull 메소드 테스트")
    void countByBusStopRouteIdListIsNotNull() {
        int cnt = busStopInfoRepo.countByBusStopRouteIdListIsNotNull();

        assertThat(cnt).isEqualTo(0);
    }

    @Test
    @DisplayName("BusStopInfoRepo 의 CountAll 메소드 테스트")
    void countBusStopInfo(){
        int i = busStopInfoRepo.countAllBy();

        assertThat(i).isEqualTo(3543);
    }

    @Test
    @DisplayName("BusStopInfoRepo 의 findAllByBusStopId 메소드 테스트")
    void findAllByBusStopId() {
        BusStopInfo testResult = busStopInfoRepo.findAllByBusStopId("192011433");
        log.info("BusStopInfoRepo: {}",testResult);
        log.info("BusStopInfoRepo Route List : {}",testResult.getBusStopRouteIdList());
    }


    @Test
    @DisplayName("BusRouteInfo 의 findBusRouteInfoByBrtName 메소드 테스트")
    void findBusRouteInfoByBrtName() {
        BusRouteInfo busRouteInfoByBrtName = busRouteInfoRepo.findBusRouteInfoByBrtName("순환21(명촌차고지(기점) 순환)");

        assertThat(busRouteInfoByBrtName.getBrtNo()).isEqualTo("6621");
    }

    @Test
    @DisplayName("BusStopLocatoin 의 searchNear500MBusStop 메소드 테스트")
    void searchNear500MBusStop() {
        RangeLatLonDto testRangeLatLonDto = RangeLatLonDto.builder()
                .minLatitude(35.321891785495495)
                .maxLatitude(35.3309007945045)
                .minLongitude(129.2713539029873)
                .maxLongitude(129.28239609701268)
                .build();

        List<BusStopInfo> near500MBusStop = busStopLocationRepo.searchNear500MBusStop(testRangeLatLonDto);
        log.info("near500MBusStop: {}",near500MBusStop);
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
