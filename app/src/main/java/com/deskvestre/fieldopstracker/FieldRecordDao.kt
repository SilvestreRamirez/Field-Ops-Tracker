package com.deskvestre.fieldopstracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldRecordDao {

    @Query("SELECT * FROM field_records")
    fun getAll(): Flow<List<FieldRecord>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(fieldRecord: FieldRecord): Long

    @Query("SELECT * FROM field_records where serverId is null")
    fun getAllPending(): Flow<List<FieldRecord>>

}