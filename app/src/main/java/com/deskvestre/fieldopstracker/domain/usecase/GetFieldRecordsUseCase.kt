package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow

class GetFieldRecordsUseCase(private val fieldRecordRepository: FieldRecordRepository) {
    suspend operator fun invoke(): Flow<Result<List<FieldRecord>>> {
        return fieldRecordRepository.getFieldRecords()
    }
}