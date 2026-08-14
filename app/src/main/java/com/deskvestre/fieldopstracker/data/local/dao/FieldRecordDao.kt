package com.deskvestre.fieldopstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldRecordDao {

    @Query("SELECT * FROM field_records")
    fun getAll(): Flow<List<FieldRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fieldRecord: FieldRecordEntity): Long

    @Query("SELECT * FROM field_records where isSynced = 0")
    fun getAllPending(): Flow<List<FieldRecordEntity>>

    @Query("SELECT * FROM field_records where serverId = :id")
    suspend fun getByServerId(id: String): FieldRecordEntity?

}