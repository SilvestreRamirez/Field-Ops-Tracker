package com.deskvestre.fieldopstracker

import android.content.Context
import androidx.room.Room

class AppContainer(context: Context) {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "field-tracker").build()
    }

    val repository: FieldRecordRepository by lazy {
        FieldRecordRepository(database.fieldRecordDao())
    }

}