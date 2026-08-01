package com.deskvestre.fieldopstracker.domain.repository

import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord
import kotlinx.coroutines.flow.Flow

interface FieldRecordRepository {
    fun getFieldRecords(): Flow<Result<List<FieldRecord>>>
    suspend fun addFieldRecord(fieldRecord: FieldRecord): Result<Long>
}