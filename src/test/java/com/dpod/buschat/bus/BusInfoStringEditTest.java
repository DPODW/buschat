package com.dpod.buschat.bus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BusInfoStringEditTest {

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


    @Test
    @DisplayName("배열을 문자열로 변환")
    void arrayToString(){
        int[] arr = {1, 2, 3, 4, 5};
        String result = Arrays.toString(arr).replace("[", "").replace("]", "");

        log.info(result);
        assertThat(result).isEqualTo("1, 2, 3, 4, 5");
    }


    @Test
    @DisplayName("문자열을 list 로 변환")
    void StringToArray(){
       String input = "0500, 0515, 0530, 0550, 0610, 0630, 2030, 2100, 2140, 2240";
       List<String> list = Arrays.asList(input.split(",\\s*")); //이스케이프 처리 (공백이 1개 이상 일때도 처리[ * ] )

       assertThat(list.get(3)).isEqualTo("0515");
    }


    @Test
    @DisplayName("현재 시간 이후의 시간표 가져오기")
    void getAfterTimeTable(){
        String input = "0500, 0515, 0530, 0550, 0610, 0630, 2030, 2100, 2140";
        List<String> list = Arrays.asList(input.split(",\\s*"));

        LocalTime now = LocalTime.now();
        List<LocalTime> localTimeTypeList = new ArrayList<>();

        for (String time : list) {
            localTimeTypeList.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm")));
        }

        for (LocalTime localTime : localTimeTypeList) {
            if (localTime.isAfter(now)) {
                log.info("현재 기준 다음 출발 시간 => {}", localTime);

            } else if(localTimeTypeList.get(localTimeTypeList.size() - 1).isBefore(now)) {
                log.info("이후 시간 없음(다음날 첫차 정보) => {}", localTimeTypeList.get(0));

            }
        }
    }


    @Test
    @DisplayName("현재 시간 밀리세컨으로 가져오기")
    void getNowTimeMs(){
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
        log.info("현재 시간 ms : {}",System.currentTimeMillis());
        log.info("현재 시간 ms(format) : {}",sdf);
    }


    @Test
    @DisplayName("문자열 확인")
    void checkTextContain(){
        String testText = "{\"location\":\"validate-001\",\"longitude\":129.236992,\"latitude\":35.5794944}";

        assertThat(testText).contains("validate-001");
    }
}
