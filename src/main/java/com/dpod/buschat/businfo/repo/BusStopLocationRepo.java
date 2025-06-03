package com.dpod.buschat.businfo.repo;

import com.dpod.buschat.businfo.entity.BusStopInfo;
import com.dpod.buschat.location.dto.RangeLatLonDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusStopLocationRepo {

    List<BusStopInfo> searchNear500MBusStop(RangeLatLonDto rangeLatLonDto);
}
