package com.dpod.buschat.businfo.repo.bus;

import com.dpod.buschat.businfo.entity.BusRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteInfoRepo extends JpaRepository<BusRouteInfo,Long> {
}
