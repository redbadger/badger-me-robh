package com.redbadger.badgerme_jetpack.ui.events

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class EventsViewModel: ViewModel() {
    val timeFilter = mutableStateOf("Today")
    val tomorrow = mutableStateOf(-1)
    val thisWeek = mutableStateOf(-1)
    val nextWeek = mutableStateOf(-1)
    val later = mutableStateOf(-1)

    val food = mutableStateOf(false)
    val drinks = mutableStateOf(false)
    val coffee = mutableStateOf(false)
    val chats =  mutableStateOf(false)
    val walks =  mutableStateOf(false)
    val hugs =  mutableStateOf(false)
}