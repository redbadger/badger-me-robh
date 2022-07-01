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

    fun getInterests() {
        viewModelScope.launch {
            val response = badgerApi.getInterests()
            if (response.isSuccessful) {
                response.body()!!.forEach{
                    interests[it.name] = TrackedInterest(it.interestId, mutableStateOf(false))
                }
            }
        }
    }

    data class TrackedInterest(
        val interestId: String,
        val interested: MutableState<Boolean>
    )
}