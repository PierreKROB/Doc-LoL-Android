package com.pkrob.ApiDocLoL.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ClickerViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
    private val _clickCount = MutableStateFlow(sharedPreferences.getInt("clickCount", 0))
    val clickCount: StateFlow<Int> = _clickCount

    fun onButtonClick() {
        _clickCount.value += 1
        sharedPreferences.edit().putInt("clickCount", _clickCount.value).apply()
    }
}
