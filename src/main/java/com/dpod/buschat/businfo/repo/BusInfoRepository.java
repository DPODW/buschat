package com.dpod.buschat.businfo.repo;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusInfoRepository extends JpaRepository<BusStopInfo,Long> {

    List<BusStopInfo> findAllByBusStopName(String busStopName);

}
