package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFieldRecordUseCase @Inject constructor(
    private val fieldRecordRepository: FieldRecordRepository
) {
    operator fun invoke(): Flow<List<FieldRecord>> {
        return fieldRecordRepository.observeAll()
    }
}