package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import javax.inject.Inject

class AddFieldRecordUseCase @Inject constructor(
    private val repository: FieldRecordRepository
) {
    suspend operator fun invoke(record: FieldRecord) {
        return repository.add(record)
    }
}