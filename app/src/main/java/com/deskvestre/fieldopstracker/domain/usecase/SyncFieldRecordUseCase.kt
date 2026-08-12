package com.deskvestre.fieldopstracker.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import javax.inject.Inject

class SyncFieldRecordUseCase @Inject constructor(
    private val repository: FieldRecordRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke() {
        return repository.sync()
    }
}