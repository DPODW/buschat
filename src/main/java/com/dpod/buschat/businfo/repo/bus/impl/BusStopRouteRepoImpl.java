package com.dpod.buschat.businfo.repo.bus.impl;

import com.dpod.buschat.businfo.dto.BusRouteRoadInfoDto;
import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.entity.QBusRouteInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.dpod.buschat.businfo.repo.bus.BusStopInfoRepo;
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
        //동시성 이슈 방지를 위해 쿼리에 조건 삽입
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

    @Transactional
    @Override
    public void updateBusStopRoute(BusRouteRoadInfoDto busRouteRoadInfoDto) {
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        String busStopRouteIdList = queryFactory.select(qBusStopInfo.busStopRouteIdList)
                .from(qBusStopInfo)
                .where(qBusStopInfo.busStopId.eq(busRouteRoadInfoDto.getStopId()))
                .fetchOne();

        if(!busStopRouteIdList.contains(busRouteRoadInfoDto.getRouteId())) {
            //정류장 노선 정보에 없는 노선만 UPDATE 쿼리 실행
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


    @Override
    public List<BusStopRouteInfoDto> searchBusRouteDetail(List<String> busStopRouteIdSplit) {
        QBusRouteInfo qBusRouteInfo = QBusRouteInfo.busRouteInfo;
        QBusStopInfo qStBusStopInfo = new QBusStopInfo("qStBusStopInfo");
        QBusStopInfo qEdBusStopInfo = new QBusStopInfo("qEdBusStopInfo");

        return queryFactory.select(Projections.fields(BusStopRouteInfoDto.class,
                        qBusRouteInfo.brtId, qBusRouteInfo.brtName, qBusRouteInfo.stopStId.as("busStopStId"), qStBusStopInfo.busStopName.as("busStopStName"),
                        qBusRouteInfo.stopEdId.as("busStopEdId"), qEdBusStopInfo.busStopName.as("busStopEdName")))
                .from(qBusRouteInfo)
                .join(qStBusStopInfo)
                .on(qBusRouteInfo.stopStId.eq(qStBusStopInfo.busStopId))
                .join(qEdBusStopInfo)
                .on(qBusRouteInfo.stopEdId.eq(qEdBusStopInfo.busStopId))
                .where(qBusRouteInfo.brtId.in(busStopRouteIdSplit))
                .fetch();
    }

}
