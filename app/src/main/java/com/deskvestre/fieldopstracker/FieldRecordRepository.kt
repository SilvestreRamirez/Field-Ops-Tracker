package com.deskvestre.fieldopstracker

import kotlinx.coroutines.flow.Flow


class FieldRecordRepository(
    private val dao: FieldRecordDao
) {
    fun observePending(): Flow<List<FieldRecord>> = dao.getAllPending()
    suspend fun add(record: FieldRecord) = dao.insert(record)
}