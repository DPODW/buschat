package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
public class BusRouteInfoTest {

    private final BusStopRouteRepo busStopRouteRepo;

    public BusRouteInfoTest(BusStopRouteRepo busStopRouteRepo) {
        this.busStopRouteRepo = busStopRouteRepo;
    }


    @Test
    @DisplayName("| 기준으로 노선 ID 를 자르는 테스트")
    void splitBusRouteIdTest() {
        String testBusRouteIdList = "1231|4321|9876";

        List<String> testBusRouteId = List.of(testBusRouteIdList.split("\\|"));
        //이스케이프 처리

        Assertions.assertThat(testBusRouteId.get(0)).isEqualTo("1231");
        Assertions.assertThat(testBusRouteId.get(1)).isEqualTo("4321");
        Assertions.assertThat(testBusRouteId.get(2)).isEqualTo("9876");
    }

}
