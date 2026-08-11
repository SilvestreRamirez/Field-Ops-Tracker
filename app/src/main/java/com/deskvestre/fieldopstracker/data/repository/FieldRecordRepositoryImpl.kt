package com.deskvestre.fieldopstracker.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.deskvestre.fieldopstracker.data.remote.api.FieldOpsApi
import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import com.deskvestre.fieldopstracker.data.local.mappers.toDomain
import com.deskvestre.fieldopstracker.data.local.mappers.toEntity
import com.deskvestre.fieldopstracker.data.remote.mappers.toDomain
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FieldRecordRepositoryImpl @Inject constructor(
    private val dao: FieldRecordDao,
    private val api: FieldOpsApi
) : FieldRecordRepository {
    override fun observePending(): Flow<List<FieldRecord>> =
        dao.getAllPending().map { entities -> entities.map { it.toDomain() } }

    override suspend fun add(record: FieldRecord) {
        dao.insert(record.toEntity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sync() {
        val remoteRecords = api.getRemoteRecords()
        remoteRecords.forEach { dto ->
            dao.insert(dto.toDomain().toEntity())
        }
    }
}