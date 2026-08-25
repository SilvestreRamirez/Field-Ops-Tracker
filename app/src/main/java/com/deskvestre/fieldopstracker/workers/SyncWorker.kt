package com.deskvestre.fieldopstracker.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
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
            val hasChanges = syncFieldRecordUseCase()
            if (hasChanges) {
                showChangeNotification()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showChangeNotification() {
        val channelId = "change_notification"
        val manager = applicationContext.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            channelId,
            "Change notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Changes detected")
            .setContentText("Changes from other advisors detected")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            manager.notify(2, notification)
        }
    }
}