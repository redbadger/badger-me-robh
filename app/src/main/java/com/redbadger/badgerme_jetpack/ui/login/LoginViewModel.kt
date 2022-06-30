package com.redbadger.badgerme_jetpack.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val loading = mutableStateOf(false)
}