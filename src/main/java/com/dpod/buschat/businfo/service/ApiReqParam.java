package com.dpod.buschat.businfo.service;

public class ApiReqParam {

    public static final String BUS_INFO_PAGE_NO = "1";
    public static final String BUS_INFO_TOTAL_COUNT = "5000";
    /**
     * API 요청 파라미터
     * 요청시, 1페이지에 모든 데이터를 출력하기 위해 요청 행 개수를 5000으로 설정 (모든 데이터 개수가 5000개 이상일시 숫자 증가 필요)
     * 아래는 API 요구 파라미터와 연결되는 상수 설명
     * pageNo = BUS_INFO_PAGE_NO
     * numOfRows = BUS_INFO_TOTAL_COUNT
     * **/
}
