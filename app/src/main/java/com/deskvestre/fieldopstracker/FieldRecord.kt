package com.deskvestre.fieldopstracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "field_records")
data class FieldRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val localId: String,
    val serverId: String? = null,
    val gpsLat: Double,
    val gpsLng: Double,
    val notes: String,
    val timestamp: Long
)