package com.deskvestre.fieldopstracker.root.domain.usecase

import com.deskvestre.fieldopstracker.FieldRecord
import com.deskvestre.fieldopstracker.root.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow

class GetFieldRecordsUseCase(private val fieldRecordRepository: FieldRecordRepository) {
    suspend operator fun invoke(): Flow<Result<List<FieldRecord>>> {
        return fieldRecordRepository.getFieldRecords()
    }
}