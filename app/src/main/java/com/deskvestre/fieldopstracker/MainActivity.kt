package com.deskvestre.fieldopstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.deskvestre.fieldopstracker.AppBase
import com.deskvestre.fieldopstracker.MainViewModel
import com.deskvestre.fieldopstracker.MainViewModelFactory
import com.deskvestre.fieldopstracker.root.ui.theme.FieldOpsTrackerTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {  // sin @Composable
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as AppBase).container

        val viewModel: MainViewModel by viewModels {
            MainViewModelFactory(appContainer.repository)
        }

        setContent {
            FieldOpsTrackerTheme {
                val pending by viewModel.pendingFieldRecords.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    pending.forEach {
                        Text(text = it.toString())
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FieldOpsTrackerTheme {
        Greeting("Android")
    }
}