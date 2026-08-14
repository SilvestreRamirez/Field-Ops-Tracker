package com.deskvestre.fieldopstracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "field_records")
data class FieldRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val localId: String,
    val serverId: String? = null,
    val gpsLat: Double,
    val gpsLng: Double,
    val notes: String,
    val timestamp: Long,
    val isSynced: Boolean = false,
)