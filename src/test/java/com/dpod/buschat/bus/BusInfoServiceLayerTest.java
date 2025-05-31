package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.service.BusTimeTableService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class BusInfoServiceLayerTest {

    private final BusTimeTableService busTimeTableService;

    @Autowired
    public BusInfoServiceLayerTest(BusTimeTableService busTimeTableService) {
        this.busTimeTableService = busTimeTableService;
    }

    @Test
    @DisplayName("getAvailableTimeTable 메소드 테스트 (SERVICE")
    void getAvailableTimeTable() {
        List<String> availableTimeTable = busTimeTableService.getAvailableTimeTable("196000447");
        log.info("시간표 조회 결과 : {}",availableTimeTable);
        Assertions.assertThat(availableTimeTable.get(0)).isEqualTo("0505");
    }
}
