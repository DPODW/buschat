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


    public BusStopRouteRepoImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    @Override
    public void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto) {
        //저장 ~ 수정이 마지막 요청 1개만 성공하는 에러 발생 -> 동시성 이슈로 추정됌
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

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
