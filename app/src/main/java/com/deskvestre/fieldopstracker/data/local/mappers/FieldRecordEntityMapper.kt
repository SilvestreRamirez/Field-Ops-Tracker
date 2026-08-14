package com.deskvestre.fieldopstracker.data.local.mappers

import com.deskvestre.fieldopstracker.data.local.entity.FieldRecordEntity
import com.deskvestre.fieldopstracker.domain.model.FieldRecord


fun FieldRecordEntity.toDomain(): FieldRecord {
    return FieldRecord(
        id = id,
        localId = localId,
        serverId = serverId,
        gpsLat = gpsLat,
        gpsLng = gpsLng,
        notes = notes,
        timestamp = timestamp,
        isSynced = isSynced
    )
}

fun FieldRecord.toEntity(): FieldRecordEntity {
    return FieldRecordEntity(
        id = id,
        localId = localId,
        serverId = serverId,
        gpsLat = gpsLat,
        gpsLng = gpsLng,
        notes = notes,
        timestamp = timestamp,
        isSynced = isSynced
    )
}
