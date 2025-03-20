package com.dpod.buschat.businfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusRouteInfo is a Querydsl query type for BusRouteInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusRouteInfo extends EntityPathBase<BusRouteInfo> {

    private static final long serialVersionUID = -1908165588L;

    public static final QBusRouteInfo busRouteInfo = new QBusRouteInfo("busRouteInfo");

    public final StringPath brtId = createString("brtId");

    public final StringPath brtName = createString("brtName");

    public final StringPath brtNo = createString("brtNo");

    public final StringPath brtTimeTable = createString("brtTimeTable");

    public final StringPath brtType = createString("brtType");

    public final StringPath busClass = createString("busClass");

    public final StringPath company = createString("company");

    public final StringPath direction = createString("direction");

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public final StringPath stopEdId = createString("stopEdId");

    public final StringPath stopStId = createString("stopStId");

    public QBusRouteInfo(String variable) {
        super(BusRouteInfo.class, forVariable(variable));
    }

    public QBusRouteInfo(Path<? extends BusRouteInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusRouteInfo(PathMetadata metadata) {
        super(BusRouteInfo.class, metadata);
    }

}

