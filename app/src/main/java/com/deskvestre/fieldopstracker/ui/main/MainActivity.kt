package com.deskvestre.fieldopstracker.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.deskvestre.fieldopstracker.ui.theme.FieldOpsTrackerTheme
import com.deskvestre.fieldopstracker.ui.viemodel.FieldRecordUiState
import com.deskvestre.fieldopstracker.ui.viemodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {  // sin @Composable
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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