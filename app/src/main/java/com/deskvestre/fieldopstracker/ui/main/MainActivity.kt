package com.deskvestre.fieldopstracker.ui.main

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.deskvestre.fieldopstracker.ui.theme.FieldOpsTrackerTheme
import com.deskvestre.fieldopstracker.ui.viemodel.FieldRecordUiState
import com.deskvestre.fieldopstracker.ui.viemodel.MainViewModel
import com.deskvestre.fieldopstracker.workers.ReminderWorker
import com.deskvestre.fieldopstracker.workers.SyncWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            enqueueReminderWorker(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {  // sin @Composable
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //make sync request
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
        //enqueue task to sync
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "field_record_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        //request notification permission to user
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                //permission granted enqueue reminder worker
                enqueueReminderWorker(this)
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                //show rationale
            }

            else -> {
                //request permission
                requestNotificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

        }


        setContent {
            FieldOpsTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: MainViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsState()
                    val syncState by viewModel.syncState.collectAsState()

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        SyncButton(
                            onClick = { viewModel.sync() }
                        )

                        if (syncState.isSyncing) {
                            CircularProgressIndicator()
                        }

                        syncState.syncError?.let { error ->
                            Text(text = " Error to sync $error")

                        }

                        when (uiState) {
                            is FieldRecordUiState.Loading -> {
                                Text(text = "Loading...")
                            }

                            is FieldRecordUiState.Success -> {
                                val records = (uiState as FieldRecordUiState.Success).records
                                records.forEach {
                                    Text(text = it.toString())
                                }
                            }

                            is FieldRecordUiState.Error -> {
                                Text(text = "Error: ${(uiState as FieldRecordUiState.Error).message}")
                            }

                        }

                    }
                }
            }
        }
    }

}


private fun enqueueReminderWorker(context: Context) {
    val reminderRequest =
        PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "field_record_reminder",
        ExistingPeriodicWorkPolicy.KEEP,
        reminderRequest
    )
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun SyncButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "Sync")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    FieldOpsTrackerTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
                SyncButton(
                    onClick = { }
                )
            }

        }
    }
}