package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EventsViewModel: ViewModel() {
    val timeFilter = mutableStateOf("Today")
    val tomorrow = mutableStateOf(-1)
    val thisWeek = mutableStateOf(-1)
    val nextWeek = mutableStateOf(-1)
    val later = mutableStateOf(-1)
}