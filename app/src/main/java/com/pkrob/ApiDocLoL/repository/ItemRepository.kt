package com.pkrob.ApiDocLoL.repository

import com.pkrob.ApiDocLoL.model.ItemsResponse
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository {
    suspend fun getItems(version: String): ItemsResponse? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.dDragonApi.getAllItems(version)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}