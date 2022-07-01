package com.redbadger.badgerme_jetpack.ui.setup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbadger.badgerme_jetpack.util.BadgerApiInterface
import com.redbadger.badgerme_jetpack.util.BadgerInterest
import com.redbadger.badgerme_jetpack.util.RetrofitHelper
import kotlinx.coroutines.launch

class InterestSetupViewModel(): ViewModel() {

    private val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val interests =  mutableMapOf<String, TrackedInterest>()

    fun setInterests(authToken: String) {
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Food"))
        }
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Drinks"))
        }
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Coffee"))
        }
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Walks"))
        }
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Hugs"))
        }
        viewModelScope.launch {
            badgerApi.addInterest(authToken, BadgerInterest("", "Chats"))
        }
    }

    fun getInterests(authToken: String, completed: MutableState<Boolean>) {
        viewModelScope.launch {
            val response = badgerApi.getInterests(authToken)
            if (response.isSuccessful) {
                response.body()!!.forEach{
                    interests[it.name] = TrackedInterest(it.id, mutableStateOf(false))
                }
                completed.value = true
            }
        }
    }

    data class TrackedInterest(
        val interestId: String,
        val interested: MutableState<Boolean>
    )
}