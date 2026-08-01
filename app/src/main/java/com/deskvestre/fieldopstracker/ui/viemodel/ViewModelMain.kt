package com.deskvestre.fieldopstracker.ui.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecord
import com.deskvestre.fieldopstracker.domain.repository.FieldRecordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModelMain(
    private val fieldRecordRepository: FieldRecordRepository
) : ViewModel() {

    val pending: StateFlow<Result<List<FieldRecord>>> = fieldRecordRepository.getFieldRecords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.success(emptyList())
        )


    fun addFieldRecord(fieldRecord: FieldRecord) {
        viewModelScope.launch {
            fieldRecordRepository.addFieldRecord(fieldRecord)
        }
    }
}