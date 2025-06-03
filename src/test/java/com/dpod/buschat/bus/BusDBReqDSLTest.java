package com.dpod.buschat.bus;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.repo.BusStopLocationRepo;
import com.dpod.buschat.businfo.repo.impl.BusStopLocationRepoImpl;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusDBReqDSLTest {

    @Autowired
    private EntityManager entityManager;

    private BusStopLocationRepo busStopLocationRepo;

    @BeforeEach
    void setUp() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        this.busStopLocationRepo = new BusStopLocationRepoImpl(queryFactory);  // 쿼리DSL 테스트를 위한 수동 생성
    }

    @Test
    @DisplayName("BusStopLocatoin 의 searchNear500MBusStop 메소드 테스트")
    void searchNear500MBusStop() {
        RangeLatLonDto testRangeLatLonDto = RangeLatLonDto.builder()
                /// DUMMY DATA (월내 정류장)
                .minLatitude(35.321891785495495)
                .maxLatitude(35.3309007945045)
                .minLongitude(129.2713539029873)
                .maxLongitude(129.28239609701268)
                .build();

        List<BusStopInfo> near500MBusStop = busStopLocationRepo.searchNear500MBusStop(testRangeLatLonDto);
        log.info("near500MBusStop: {}", near500MBusStop);
    }
}
