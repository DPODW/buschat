package com.dpod.buschat.businfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusStopInfo is a Querydsl query type for BusStopInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusStopInfo extends EntityPathBase<BusStopInfo> {

    private static final long serialVersionUID = 816255899L;

    public static final QBusStopInfo busStopInfo = new QBusStopInfo("busStopInfo");

    public final StringPath busStopId = createString("busStopId");

    public final StringPath busStopMark = createString("busStopMark");

    public final StringPath busStopName = createString("busStopName");

    public final StringPath busStopRouteIdList = createString("busStopRouteIdList");

    public final StringPath busStopX = createString("busStopX");

    public final StringPath busStopY = createString("busStopY");

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public QBusStopInfo(String variable) {
        super(BusStopInfo.class, forVariable(variable));
    }

    public QBusStopInfo(Path<? extends BusStopInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusStopInfo(PathMetadata metadata) {
        super(BusStopInfo.class, metadata);
    }

}

