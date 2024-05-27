package com.pkrob.ApiDocLoL.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkrob.ApiDocLoL.model.ChampionsResponse
import com.pkrob.ApiDocLoL.model.ItemsResponse
import com.pkrob.ApiDocLoL.network.ApiService
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    private val _versions = MutableStateFlow<List<String>>(emptyList())
    val versions: StateFlow<List<String>> = _versions

    private val _selectedVersion = MutableStateFlow<String?>(null)
    val selectedVersion: StateFlow<String?> = _selectedVersion

    private val _champions = MutableStateFlow<ChampionsResponse?>(null)
    val champions: StateFlow<ChampionsResponse?> = _champions

    private val _items = MutableStateFlow<ItemsResponse?>(null)
    val items: StateFlow<ItemsResponse?> = _items

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getVersions()
    }

    private fun getVersions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.dDragonApi.getVersions()
                if (response.isSuccessful) {
                    _versions.value = response.body() ?: emptyList()
                    _selectedVersion.value = response.body()?.firstOrNull()
                    _selectedVersion.value?.let {
                        getAllChampions(it)
                        getAllItems(it)
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception getting versions", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectVersion(version: String) {
        _selectedVersion.value = version
        getAllChampions(version)
        getAllItems(version)
    }

    private fun getAllChampions(version: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllChampions(version)
                if (response.isSuccessful) {
                    _champions.value = response.body()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception getting champions", e)
            }
        }
    }

    private fun getAllItems(version: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllItems(version)
                if (response.isSuccessful) {
                    _items.value = response.body()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception getting items", e)
            }
        }
    }
}
