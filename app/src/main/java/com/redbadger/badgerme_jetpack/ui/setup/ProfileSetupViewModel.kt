package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbadger.badgerme_jetpack.util.BadgerApiInterface
import com.redbadger.badgerme_jetpack.util.BadgerUser
import com.redbadger.badgerme_jetpack.util.RetrofitHelper
import kotlinx.coroutines.launch

class ProfileSetupViewModel: ViewModel()  {
    private val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val user = mutableStateOf(BadgerUser(null, "", "", ""))
    fun getUser(userId: String, authToken: String){
        viewModelScope.launch {
            val response = badgerApi.getUser(authToken, userId)
            if (response.isSuccessful) {
                user.value = response.body()!!
            }
        }
    }
}