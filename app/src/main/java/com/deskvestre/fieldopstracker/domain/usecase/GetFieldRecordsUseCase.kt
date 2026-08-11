package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.data.local.entity.FieldRecordEntity
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow

class GetFieldRecordsUseCase(private val fieldRecordRepository: FieldRecordRepository) {
//    suspend operator fun invoke(): Flow<Result<List<FieldRecordEntity>>> {
//        return fieldRecordRepository.getFieldRecords()
//    }
}