package com.deskvestre.fieldopstracker.root.data.local

import com.deskvestre.fieldopstracker.FieldRecordDao
import com.deskvestre.fieldopstracker.FieldRecord

class LocalDataSource(private val fieldRecordDao: FieldRecordDao) {

    fun getFieldRecords() = fieldRecordDao.getAll()

    suspend fun createFieldRecord(fieldRecord: FieldRecord) = fieldRecordDao.insert(fieldRecord)
}