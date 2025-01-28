package com.dpod.buschat.businfo.repo.bus.impl;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.QBusRouteInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopRouteRepo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public List<BusStopRouteInfoDto> searchBusRouteDetail(List<String> busStopRouteIdSplit) {
        QBusRouteInfo qBusRouteInfo = QBusRouteInfo.busRouteInfo;
        QBusStopInfo qStBusStopInfo = new QBusStopInfo("qStBusStopInfo");
        QBusStopInfo qEdBusStopInfo = new QBusStopInfo("qEdBusStopInfo");

        return queryFactory.select(Projections.fields(BusStopRouteInfoDto.class,
                        qBusRouteInfo.brtName, qBusRouteInfo.stopStId.as("busStopStId"), qStBusStopInfo.busStopName.as("busStopStName"),
                        qBusRouteInfo.stopEdId.as("busStopEdId"), qEdBusStopInfo.busStopName.as("busStopEdName")))
                .from(qBusRouteInfo)
                .join(qStBusStopInfo)
                .on(qBusRouteInfo.stopStId.eq(qStBusStopInfo.busStopId))
                .join(qEdBusStopInfo)
                .on(qBusRouteInfo.stopEdId.eq(qEdBusStopInfo.busStopId))
                .where(qBusRouteInfo.brtId.in(busStopRouteIdSplit))
                .fetch();
        // WHERE ~ IN 으로 개선하여서 반복 요청을 줄임.
    }

}
