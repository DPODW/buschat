package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.entity.BusRouteInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteInfoRepo extends JpaRepository<BusRouteInfo,Long> {

    BusRouteInfo findBusRouteInfosBySequence(Long sequence);

    BusRouteInfo findBusRouteInfosByBrtId(String busRouteId);

    int countAllBy();

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE BUSROUTE_INFO AUTO_INCREMENT = 1", nativeQuery = true)
    void resetBusRouteInfoSequence();

}
