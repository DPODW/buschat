package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.entity.QBusRouteInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class BusStopRouteRepoImpl implements BusStopRouteRepo {

    private final JPAQueryFactory queryFactory;

    private final BusInfoRepository busInfoRepository;


    public BusStopRouteRepoImpl(JPAQueryFactory queryFactory, BusInfoRepository busInfoRepository) {
        this.queryFactory = queryFactory;
        this.busInfoRepository = busInfoRepository;
    }

    @Transactional
    @Override
    public void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto) {
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        // 하나의 업데이트 쿼리로 처리
        queryFactory.update(qBusStopInfo)
                .where(qBusStopInfo.busStopId.eq(busStopRouteInfoDto.getStopId()))
                .set(
                        qBusStopInfo.busStopRoute,
                        new CaseBuilder()
                                .when(qBusStopInfo.busStopRoute.isNull())
                                .then(busStopRouteInfoDto.getRouteId())
                                .otherwise(qBusStopInfo.busStopRoute.concat("|" + busStopRouteInfoDto.getRouteId()))
                )
                .execute();
    }
}
