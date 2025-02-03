package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.entity.BusRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteInfoRepo extends JpaRepository<BusRouteInfo,Long> {

    BusRouteInfo findBusRouteInfosBySequence(Long sequence);

    int countAllBy();

}
