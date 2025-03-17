package com.dpod.buschat.bus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class StringEditTest {

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

    @DisplayName("현재 시간 특정 형식으로 가져오기")
    @Test
    void formatTimeText() {
        LocalTime now = LocalTime.now();
        String hour = String.valueOf(now.getHour());
        String minute = String.valueOf(now.getMinute());
        if(now.getHour() < 10) {
            hour = "0" + hour;
        }
        String result = hour.concat(minute);
        log.info(result);
    }

    @Test
    @DisplayName("숫자가 아닌 문자열 제거")
    void replaceNotNumber(){
        String input = "219(신정2동행정복지센터입구 방면)";

        int idx = input.indexOf("(");
        String substring = input.substring(0, idx);

        assertThat(substring).isEqualTo("219");
    }


    @Test
    @DisplayName("첫 글자가 숫자인지 판별")
    void checkFirstWord(){
        String firstNumber = "219(신정2동행정복지센터입구 방면)";
        boolean numberMatches = firstNumber.matches("^[0-9].*");

        String firstStr = "울주21";
        boolean stringMatches = firstStr.matches("^[0-9].*");

        assertThat(numberMatches).isTrue();
        assertThat(stringMatches).isFalse();
    }
}
