package com.onix.internship.backgroundanimation.ui.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.onix.internship.backgroundanimation.events.SingleLiveEvent

class SecondViewModel : ViewModel() {

    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent
}