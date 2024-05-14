package com.pkrob.ApiDocLoL.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkrob.ApiDocLoL.model.Champion
import com.pkrob.ApiDocLoL.model.ChampionDetail
import com.pkrob.ApiDocLoL.repository.ChampionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChampionViewModel(private val version: String) : ViewModel() {
    private val repository = ChampionRepository()
    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions: StateFlow<List<Champion>> = _champions

    private val _championDetail = MutableStateFlow<ChampionDetail?>(null)
    val championDetail: StateFlow<ChampionDetail?> = _championDetail

    init {
        getChampions(version)
    }

    private fun getChampions(version: String) {
        viewModelScope.launch {
            val response = repository.getChampions(version)
            response?.data?.values?.let {
                _champions.value = it.toList()
            }
        }
    }

    fun getChampionDetail(version: String, championId: String) {
        viewModelScope.launch {
            val response = repository.getChampionDetail(version, championId)
            response?.data?.values?.firstOrNull()?.let {
                _championDetail.value = it
            }
        }
    }
}
