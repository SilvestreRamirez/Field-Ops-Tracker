package com.deskvestre.fieldopstracker.data.local

import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord

class LocalDataSource(private val fieldRecordDao: FieldRecordDao) {

    fun getFieldRecords() = fieldRecordDao.getAll()

    suspend fun createFieldRecord(fieldRecord: FieldRecord) = fieldRecordDao.insert(fieldRecord)
}