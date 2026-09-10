package com.deskvestre.fieldopstracker.utilities

import android.content.Context
import java.io.File

fun createImageFile(context: Context): File {
    val timestamp = System.currentTimeMillis()
    val photosDir = File(context.getExternalFilesDir(null), "photos")
    if (!photosDir.exists()) photosDir.mkdirs()
    return File(photosDir, "FIELD_${timestamp}.jpg")
}