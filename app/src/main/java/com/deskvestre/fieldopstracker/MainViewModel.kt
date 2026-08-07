package com.deskvestre.fieldopstracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FieldRecordRepository
) : ViewModel() {

    val pendingFieldRecords: StateFlow<List<FieldRecord>> = repository.observePending().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun add(record: FieldRecord) {
        viewModelScope.launch {
            repository.add(record)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sync() {
        viewModelScope.launch {
            repository.sync()
        }
    }
}