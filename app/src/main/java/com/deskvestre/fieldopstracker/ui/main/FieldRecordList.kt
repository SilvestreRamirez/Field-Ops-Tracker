package com.deskvestre.fieldopstracker.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.deskvestre.fieldopstracker.ui.navhost.Routes
import com.deskvestre.fieldopstracker.ui.viemodel.FieldRecordUiState
import com.deskvestre.fieldopstracker.ui.viemodel.MainViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FieldRecordList(navController: NavHostController, viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val syncState by viewModel.syncState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_RECORD) }) {
                Icon(Icons.Default.Add, contentDescription = "Add record")
            }
        }
    ) { innerPadding ->
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
                    Text(text = "Records size: ${records.size}")
                    LazyColumn {
                        items(records, key = { it.id }) { record ->
                            FieldRecordItem(record)
                        }
                    }
                }

                is FieldRecordUiState.Error -> {
                    Text(text = "Error: ${(uiState as FieldRecordUiState.Error).message}")
                }

            }

        }
    }

}