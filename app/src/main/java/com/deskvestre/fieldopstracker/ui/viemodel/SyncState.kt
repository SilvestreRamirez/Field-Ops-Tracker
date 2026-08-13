package com.deskvestre.fieldopstracker.ui.viemodel

data class SyncState(
    val isSyncing: Boolean = false,
    val syncError: String? = null,
)
