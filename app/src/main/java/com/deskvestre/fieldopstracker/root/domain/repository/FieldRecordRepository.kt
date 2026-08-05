package com.deskvestre.fieldopstracker.root.domain.repository

import com.deskvestre.fieldopstracker.FieldRecord
import kotlinx.coroutines.flow.Flow

interface FieldRecordRepository {
    fun getFieldRecords(): Flow<Result<List<FieldRecord>>>
    suspend fun addFieldRecord(fieldRecord: FieldRecord): Result<Long>
}