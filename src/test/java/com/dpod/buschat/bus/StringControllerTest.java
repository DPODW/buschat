package com.dpod.buschat.bus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

@Slf4j
public class StringControllerTest {

    @DisplayName("현재 시간 특정 형식으로 가져오기")
    @Test
    void testStringController() {
        LocalTime now = LocalTime.now();
        String hour = String.valueOf(now.getHour());
        String minute = String.valueOf(now.getMinute());

        if(now.getHour() < 10) {
            hour = "0" + hour;
        }

        String result = hour.concat(minute);

        log.info(result);

    }
}
