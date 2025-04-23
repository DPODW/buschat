package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.entity.BusRouteInfo;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.service.BusTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaReqTest {

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    @Autowired
    public JpaReqTest(BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo) {
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo;
    }


    @Test
    @DisplayName("JPA findAllByBusStopRouteIdListLike 메소드 테스트")
    void findAllByBusStopRouteIdListLikeTest() {
        String testBusRouteId = "%11111111%";

        List<BusStopInfo> allByBusStopRouteIdListLike = busStopInfoRepo.findAllByBusStopRouteIdListLike(testBusRouteId);

        assertThat(allByBusStopRouteIdListLike.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JPA BusRouteInfoRepo 의 CountAll 메소드 테스트")
    void countBusRouteInfo() {
        int busRouteInfoTotalCnt = busRouteInfoRepo.countAllBy();

        assertThat(busRouteInfoTotalCnt).isEqualTo(490);
    }

    @Test
    @DisplayName("JPA BusStopInfoRepo 의 countByBusStopRouteIdListIsNotNull 메소드 테스트")
    void countByBusStopRouteIdListIsNotNull() {
        int cnt = busStopInfoRepo.countByBusStopRouteIdListIsNotNull();

        assertThat(cnt).isEqualTo(0);
    }

    @Test
    @DisplayName("JPA BusStopInfoRepo 의 CountAll 메소드 테스트")
    void countBusStopInfo(){
        int i = busStopInfoRepo.countAllBy();

        assertThat(i).isEqualTo(3543);
    }

    @Test
    @DisplayName("JPA BusStopInfoRepo 의 findAllByBusStopId 메소드 테스트")
    void findAllByBusStopId() {
        BusStopInfo testResult = busStopInfoRepo.findAllByBusStopId("192011433");
        log.info("BusStopInfoRepo: {}",testResult);
        log.info("BusStopInfoRepo Route List : {}",testResult.getBusStopRouteIdList());
    }


    @Test
    @DisplayName("JPA BusRouteInfo 의 findBusRouteInfoByBrtName 메소드 테스트")
    void findBusRouteInfoByBrtName() {
        BusRouteInfo busRouteInfoByBrtName = busRouteInfoRepo.findBusRouteInfoByBrtName("순환21(명촌차고지(기점) 순환)");

        assertThat(busRouteInfoByBrtName.getBrtNo()).isEqualTo("6621");
    }

}
