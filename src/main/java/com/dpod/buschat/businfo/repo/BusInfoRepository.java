package com.dpod.buschat.businfo.repo;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusInfoRepository extends JpaRepository<BusStopInfo,Long> {
}
