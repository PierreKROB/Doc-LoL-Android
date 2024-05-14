package com.pkrob.ApiDocLoL.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkrob.ApiDocLoL.model.Item
import com.pkrob.ApiDocLoL.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val version: String) : ViewModel() {
    private val repository = ItemRepository()
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        getItems(version)
    }

    private fun getItems(version: String) {
        viewModelScope.launch {
            val response = repository.getItems(version)
            response?.data?.values?.let {
                _items.value = it.toList()
            }
        }
    }
}
