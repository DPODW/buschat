package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.entity.QBusRouteInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public void saveBusStopRoute(String busStopId, String busRouteNo) {
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        BusStopInfo allByBusStopId = busInfoRepository.findAllByBusStopId(busStopId);

        if(allByBusStopId!=null && allByBusStopId.getBusStopRoute()!=null){
            log.info("{}",busInfoRepository.findAllByBusStopId(busStopId));

            queryFactory.update(qBusStopInfo)
                    .where(qBusStopInfo.busStopId.eq(busStopId))
                    .set(qBusStopInfo.busStopRoute,qBusStopInfo.busStopRoute.concat("|"+busRouteNo))
                    .execute();
        }else
            log.info("DB에 일치하는 정류장이 없습니다. 해당 아이디:{}",busStopId);
    }
}
