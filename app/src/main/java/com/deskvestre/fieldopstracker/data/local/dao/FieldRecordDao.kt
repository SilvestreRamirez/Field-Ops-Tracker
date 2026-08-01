package com.deskvestre.fieldopstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldRecordDao {

    @Query("SELECT * FROM field_records")
    fun getAll(): Flow<List<FieldRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fieldRecord: FieldRecord): Long

    @Query("SELECT * FROM field_records where serverId is null")
    fun getAllPending(): Flow<List<FieldRecord>>

}