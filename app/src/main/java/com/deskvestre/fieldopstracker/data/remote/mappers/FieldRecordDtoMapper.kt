package com.deskvestre.fieldopstracker.data.remote.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.deskvestre.fieldopstracker.data.remote.dto.FieldRecordDto
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
fun FieldRecordDto.toDomain(): FieldRecord {
    return FieldRecord(
        id = 0L,
        localId = id,
        serverId = id,
        gpsLat = lat,
        gpsLng = lng,
        notes = note ?: "",
        timestamp = try {
            Instant.parse(createdAt).toEpochMilli()
        } catch (e: Exception) {
            System.currentTimeMillis()
        },
        isSynced = true
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun FieldRecord.toDto(): FieldRecordDto {
    return FieldRecordDto(
        id = localId,
        lat = gpsLat,
        lng = gpsLng,
        note = notes,
        createdAt = Instant.ofEpochMilli(timestamp).toString()
    )
}
