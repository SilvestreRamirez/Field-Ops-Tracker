package com.deskvestre.fieldopstracker.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deskvestre.fieldopstracker.domain.usecase.SyncFieldRecordUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncFieldRecordUseCase: SyncFieldRecordUseCase
) : CoroutineWorker(appContext, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            syncFieldRecordUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}