package com.redbadger.badgerme_jetpack.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(): ViewModel() {
    val loading = mutableStateOf(true)

    fun simulateLoading(length: Long) {
        viewModelScope.launch {
            delay(length)
            loading.value = false
        }
    }
}