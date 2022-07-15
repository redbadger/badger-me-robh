package com.redbadger.badgerme_jetpack.ui.home.events

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redbadger.badgerme_jetpack.ui.setup.InterestSetupViewModel
import com.redbadger.badgerme_jetpack.util.*
import kotlinx.coroutines.launch

class EventsViewModel: ViewModel() {
    private val badgerApi = RetrofitHelper.getInstance().create(BadgerApiInterface::class.java)
    val activities = mutableListOf<BadgerEvent>()

    val error = mutableStateOf(false)

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

    private var lastScrollIndex = 0

    private val _scrollUp = MutableLiveData(false)
    val scrollUp: LiveData<Boolean>
        get() = _scrollUp

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }

    fun getActivities(authToken: String, completed: MutableState<Boolean>) {
        viewModelScope.launch {
            val response = badgerApi.getActivities(authToken)
            if (response.isSuccessful) {
                response.body()!!.forEach{ activity ->
//                      Seems a bit dumb, but we have to query the API
//                      to get the user details for each activity
                    viewModelScope.launch {
                        val userResponse = badgerApi.getUser(authToken, activity.created_by)
                        if (userResponse.isSuccessful) {
                            activities.add(
                                BadgerEvent(
                                    name = activity.name,
                                    startTime = activity.start_time,
                                    endTime = activity.end_time,
                                    location = activity.location,
                                    created_by = userResponse.body()!!
                                )
                            )
                        }
                        else {
                            activities.add(
                                BadgerEvent(
                                    name = activity.name,
                                    startTime = activity.start_time,
                                    endTime = activity.end_time,
                                    location = activity.location,
                                    created_by = BadgerUser(
                                        null,
                                        "Unknown",
                                        "Badger",
                                        "unknown.badger@red-badger.com"
                                    )
                                )
                            )
                        }
                    }
                }
                completed.value = true
            }
            else {
                error.value = false
            }
        }
    }
}