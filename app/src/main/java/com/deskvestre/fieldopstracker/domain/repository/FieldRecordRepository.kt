package com.deskvestre.fieldopstracker.domain.repository


import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import kotlinx.coroutines.flow.Flow

interface FieldRecordRepository {
    fun observePending(): Flow<List<FieldRecord>>
    suspend fun add(record: FieldRecord)
    suspend fun sync()
}