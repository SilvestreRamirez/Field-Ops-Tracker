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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.deskvestre.fieldopstracker.ui.navhost.Routes
import com.deskvestre.fieldopstracker.ui.viemodel.FieldRecordUiState
import com.deskvestre.fieldopstracker.ui.viemodel.MainViewModel
import com.deskvestre.fieldopstracker.ui.viemodel.SyncState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FieldRecordList(navController: NavHostController, viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val syncState by viewModel.syncState.collectAsState()
    val token by viewModel.token.collectAsState()

    //get token from viewmodel
    LaunchedEffect(Unit) {
        viewModel.getToken()
    }

    FieldRecordListContent(
        uiState = uiState,
        syncState = syncState,
        token = token,
        onSyncClick = { viewModel.sync() },
        onAddClick = { navController.navigate(Routes.ADD_RECORD) },
        onAddTokenManualClick = { viewModel.addManualToken() }
    )

}

@Composable
fun FieldRecordListContent(
    uiState: FieldRecordUiState,
    syncState: SyncState,
    token: String?,
    onSyncClick: () -> Unit,
    onAddClick: () -> Unit,
    onAddTokenManualClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
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
            Button(
                onClick = onAddTokenManualClick
            ) {
                Text(text = "Save manual token")
            }

            token?.let {
                Text(text = "Token: $it")
            }

            SyncButton(
                onClick = onSyncClick
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
                    val records = uiState.records
                    Text(text = "Records size: ${records.size}")
                    LazyColumn {
                        items(records, key = { it.id }) { record ->
                            FieldRecordItem(record)
                        }
                    }
                }

                is FieldRecordUiState.Error -> {
                    Text(text = "Error: ${uiState.message}")
                }

            }

        }
    }
}