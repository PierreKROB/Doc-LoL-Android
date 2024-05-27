package com.pkrob.ApiDocLoL.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
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

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getChampions(version)
    }

    private fun getChampions(version: String) {
        viewModelScope.launch {
            try {
                val response = repository.getChampions(version)
                response?.data?.values?.let {
                    _champions.value = it.toList()
                }
            } catch (e: JsonSyntaxException) {
                _error.value = "Erreur de parsing des données : ${e.message}"
            } catch (e: Exception) {
                _error.value = "Une erreur s'est produite : ${e.message}"
            }
        }
    }

    fun getChampionDetail(version: String, championId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getChampionDetail(version, championId)
                response?.data?.values?.firstOrNull()?.let {
                    _championDetail.value = it
                }
            } catch (e: JsonSyntaxException) {
                _error.value = "Erreur de parsing des données : ${e.message}"
            } catch (e: Exception) {
                _error.value = "Une erreur s'est produite : ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
