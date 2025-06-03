package com.dpod.buschat.businfo.repo.impl;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.businfo.entity.QBusStopInfo;
import com.dpod.buschat.businfo.repo.BusStopLocationRepo;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class BusStopLocationRepoImpl implements BusStopLocationRepo {

    private final JPAQueryFactory queryFactory;

    public BusStopLocationRepoImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Transactional
    @Override
    public List<BusStopInfo> searchNear500MBusStop(RangeLatLonDto rangeLatLonDto) {
        QBusStopInfo qBusStopInfo = QBusStopInfo.busStopInfo;

        return queryFactory.selectFrom(qBusStopInfo)
                .where(qBusStopInfo.busStopLat.between(rangeLatLonDto.getMinLatitude(), rangeLatLonDto.getMaxLatitude())
                        .and(qBusStopInfo.busStopLon.between(rangeLatLonDto.getMinLongitude(), rangeLatLonDto.getMaxLongitude())))
                .fetch();

    }
}
