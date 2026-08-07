package com.deskvestre.fieldopstracker

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.deskvestre.fieldopstracker.root.ui.theme.FieldOpsTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {  // sin @Composable
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FieldOpsTrackerTheme {
                val viewModel: MainViewModel = hiltViewModel()

                val pending by viewModel.pendingFieldRecords.collectAsState()

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
                            onClick = { viewModel.sync() }
                        )
                        pending.forEach {
                            Text(text = it.toString())
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