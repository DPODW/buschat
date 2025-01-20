package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.dto.BusStopRouteInfoDto;
import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.entity.QBusRouteInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
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

    private final EntityManager em;

    public BusStopRouteRepoImpl(JPAQueryFactory queryFactory, BusInfoRepository busInfoRepository, EntityManager em) {
        this.queryFactory = queryFactory;
        this.busInfoRepository = busInfoRepository;
        this.em = em;
    }

    @Transactional
    @Override
    public void saveBusStopRoute(BusStopRouteInfoDto busStopRouteInfoDto) {
        //저장 ~ 수정이 마지막 요청 1개만 성공하는 에러 발생 -> 동시성 이슈로 추정됌

        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        BusStopInfo allByBusStopId = busInfoRepository.findAllByBusStopId(busStopRouteInfoDto.getStopId());

        if(allByBusStopId!=null){
            if(allByBusStopId.getBusStopRoute()==null){
                queryFactory.update(qBusStopInfo)
                        .where(qBusStopInfo.busStopId.eq(busStopRouteInfoDto.getStopId()))
                        .set(qBusStopInfo.busStopRoute,busStopRouteInfoDto.getRouteId())
                        .execute();
            } else {
                queryFactory.update(qBusStopInfo)
                        .where(qBusStopInfo.busStopId.eq(busStopRouteInfoDto.getStopId()))
                        .set(qBusStopInfo.busStopRoute,qBusStopInfo.busStopRoute.concat("|"+busStopRouteInfoDto.getRouteId()))
                        .execute();
            }
        }
    }
}
