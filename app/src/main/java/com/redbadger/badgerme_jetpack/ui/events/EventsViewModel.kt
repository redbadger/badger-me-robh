package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EventsViewModel: ViewModel() {
    val timeFilter = mutableStateOf("Today")
}