package com.deskvestre.fieldopstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecordEntity

@Database(entities = [FieldRecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    //room dao's
    abstract fun fieldRecordDao(): FieldRecordDao
}