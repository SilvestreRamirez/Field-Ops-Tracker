package com.deskvestre.fieldopstracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FieldRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    //room dao's
    abstract fun fieldRecordDao(): FieldRecordDao
}