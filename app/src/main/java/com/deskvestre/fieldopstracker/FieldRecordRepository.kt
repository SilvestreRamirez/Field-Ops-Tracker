package com.deskvestre.fieldopstracker

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FieldRecordRepository @Inject constructor(
    private val dao: FieldRecordDao,
    private val api: FieldOpsApi
) {
    fun observePending(): Flow<List<FieldRecord>> = dao.getAllPending()

    suspend fun add(record: FieldRecord) = dao.insert(record)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sync() {
        val remoteRecords = api.getRemoteRecords()
        remoteRecords.forEach { dto ->
            val record = FieldRecord(
                localId = dto.id,
                serverId = dto.id,
                gpsLat = dto.lat,
                gpsLng = dto.lng,
                notes = dto.note ?: "",
                timestamp = try {
                    java.time.Instant.parse(dto.createdAt).toEpochMilli()
                } catch (e: Exception) {
                    System.currentTimeMillis()
                }
            )
            dao.insert(record)
        }
    }
}