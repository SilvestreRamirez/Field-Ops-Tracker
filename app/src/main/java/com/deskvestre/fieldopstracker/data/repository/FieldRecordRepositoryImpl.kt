package com.deskvestre.fieldopstracker.data.repository

import com.deskvestre.fieldopstracker.data.local.LocalDataSource
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FieldRecordRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FieldRecordRepository {
    override fun getFieldRecords(): Flow<Result<List<FieldRecord>>> {
        return localDataSource.getFieldRecords()
            .map { fieldRecords ->
                Result.success(fieldRecords)
            }
    }

    override suspend fun addFieldRecord(fieldRecord: FieldRecord): Result<Long> {
        localDataSource.createFieldRecord(fieldRecord).let { id -> return Result.success(id) }
    }

}