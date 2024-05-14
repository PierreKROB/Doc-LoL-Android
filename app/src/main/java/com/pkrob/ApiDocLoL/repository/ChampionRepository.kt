package com.pkrob.ApiDocLoL.repository

import com.pkrob.ApiDocLoL.model.ChampionDetailResponse
import com.pkrob.ApiDocLoL.model.ChampionsResponse
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChampionRepository {
    suspend fun getChampions(version: String): ChampionsResponse? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api.getAllChampions(version)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun getChampionDetail(version: String, championId: String): ChampionDetailResponse? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api.getChampionDetail(version, championId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}
