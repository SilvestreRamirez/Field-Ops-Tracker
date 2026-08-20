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
import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val dao: FieldRecordDao,
) : CoroutineWorker(
    context,
    params
) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val pendingRecords = dao.countRecordToSync()
        if (pendingRecords > 0) {
            showNotification(pendingRecords)
        }
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(pendingRecords: Int) {
        val channelId = "sync_reminder"
        val manager = applicationContext.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            channelId,
            "Sync Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Records without sync")
            .setContentText("$pendingRecords records without sync")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            manager.notify(1, notification)
        }
    }
}