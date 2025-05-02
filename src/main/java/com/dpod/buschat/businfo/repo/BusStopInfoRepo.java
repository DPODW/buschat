package com.dpod.buschat.businfo.repo;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopInfoRepo extends JpaRepository<BusStopInfo,Long> {

    List<BusStopInfo> findAllByBusStopNameLike(String busStopName);

    List<BusStopInfo> findAllByBusStopRouteIdListLike(String busStopRouteId);

    BusStopInfo findAllByBusStopId(String busStopId);

    int countAllBy();

    int countByBusStopRouteIdListIsNotNull();

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE BUSSTOP_INFO AUTO_INCREMENT = 1", nativeQuery = true)
    void resetBusStopInfoSequence();

}
