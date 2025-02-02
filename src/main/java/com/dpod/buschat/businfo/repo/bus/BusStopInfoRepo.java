package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopInfoRepo extends JpaRepository<BusStopInfo,Long> {

    List<BusStopInfo> findAllByBusStopNameLike(String busStopName);

    List<BusStopInfo> findAllByBusStopRouteIdListLike(String busStopRouteId);

}
