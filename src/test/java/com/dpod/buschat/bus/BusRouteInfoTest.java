package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusRouteInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.dpod.buschat.businfo.service.BusInfoSaveService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusRouteInfoTest {

    private final BusStopInfoRepo busStopInfoRepo;

    private final BusRouteInfoRepo busRouteInfoRepo;

    @Autowired
    public BusRouteInfoTest(BusStopInfoRepo busStopInfoRepo, BusRouteInfoRepo busRouteInfoRepo) {
        this.busStopInfoRepo = busStopInfoRepo;
        this.busRouteInfoRepo = busRouteInfoRepo;
    }

    @Test
    @DisplayName("| 기준으로 노선 ID 를 자르는 테스트")
    void splitBusRouteIdTest() {
        String testBusRouteIdList = "1231|4321|9876";

        List<String> testBusRouteId = List.of(testBusRouteIdList.split("\\|"));
        //이스케이프 처리

        assertThat(testBusRouteId.get(0)).isEqualTo("1231");
        assertThat(testBusRouteId.get(1)).isEqualTo("4321");
        assertThat(testBusRouteId.get(2)).isEqualTo("9876");
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

}
