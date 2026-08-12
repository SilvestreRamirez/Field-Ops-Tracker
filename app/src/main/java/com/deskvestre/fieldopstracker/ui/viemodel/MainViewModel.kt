package com.deskvestre.fieldopstracker.ui.viemodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.domain.usecase.AddFieldRecordUseCase
import com.deskvestre.fieldopstracker.domain.usecase.GetFieldRecordUseCase
import com.deskvestre.fieldopstracker.domain.usecase.SyncFieldRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addFieldRecordUseCase: AddFieldRecordUseCase,
    private val syncFieldRecordUseCase: SyncFieldRecordUseCase,
    private val getFieldRecordsUseCase: GetFieldRecordUseCase,
) : ViewModel() {

    val pendingFieldRecords: StateFlow<List<FieldRecord>> = getFieldRecordsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun add(record: FieldRecord) {
        viewModelScope.launch {
            addFieldRecordUseCase(record)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sync() {
        viewModelScope.launch {
            syncFieldRecordUseCase()
        }
    }
}