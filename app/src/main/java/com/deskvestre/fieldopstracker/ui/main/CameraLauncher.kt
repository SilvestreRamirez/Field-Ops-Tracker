package com.deskvestre.fieldopstracker.ui.main

import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.deskvestre.fieldopstracker.utilities.createImageFile

class CameraLauncher {

    @Composable
    fun rememberCameraLauncher(
        onPhotoTaken: (Uri) -> Unit
    ): Pair<() -> Unit, Uri?> {
        val context = LocalContext.current
        var photoUri by remember { mutableStateOf<Uri?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                photoUri?.let { onPhotoTaken(it) }
            }
        }

        val takePhoto = {
            val file = createImageFile(context)
            val uri = FileProvider.getUriForFile(
                context,
                "com.deskvestre.fieldopstracker.fileprovider",
                file
            )
            photoUri = uri
            launcher.launch(uri)
        }

        return Pair(takePhoto, photoUri)
    }

    @Composable
    fun rememberCameraPermission(
        onGranted: () -> Unit
    ): () -> Unit {
        val context = LocalContext.current

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) onGranted()
            else
                Toast.makeText(
                    context,
                    "Permission is needed to take photos",
                    Toast.LENGTH_SHORT
                ).show()
        }

        return {
            val hasPermission = ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                onGranted()
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }
}