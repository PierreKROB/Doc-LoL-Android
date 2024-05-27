package com.pkrob.ApiDocLoL.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClickerViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClickerViewModel::class.java)) {
            return ClickerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
