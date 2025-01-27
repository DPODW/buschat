package com.dpod.buschat.businfo.repo.bus.impl;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public void saveBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        queryFactory.update(qBusStopInfo)
                .where(qBusStopInfo.busStopId.eq(busRouteRoadInfoDto.getStopId()))
                .set(
                        qBusStopInfo.busStopRouteIdList,
                        new CaseBuilder()
                                .when(qBusStopInfo.busStopRouteIdList.isNull())
                                .then(busRouteRoadInfoDto.getRouteId())
                                .otherwise(qBusStopInfo.busStopRouteIdList.concat("|" + busRouteRoadInfoDto.getRouteId()))
                )
                .execute();
    }
}
