package com.pkrob.ApiDocLoL.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChampionViewModelFactory(private val version: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChampionViewModel::class.java)) {
            return ChampionViewModel(version) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
