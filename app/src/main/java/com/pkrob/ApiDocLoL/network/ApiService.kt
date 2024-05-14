package com.pkrob.ApiDocLoL.network

import com.pkrob.ApiDocLoL.model.ChampionDetailResponse
import com.pkrob.ApiDocLoL.model.ChampionsResponse
import com.pkrob.ApiDocLoL.model.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("cdn/{version}/data/fr_FR/champion.json")
    suspend fun getAllChampions(@Path("version") version: String): Response<ChampionsResponse>

    @GET("cdn/{version}/data/fr_FR/item.json")
    suspend fun getAllItems(@Path("version") version: String): Response<ItemsResponse>

    @GET("cdn/{version}/data/fr_FR/champion/{championId}.json")
    suspend fun getChampionDetail(@Path("version") version: String, @Path("championId") championId: String): Response<ChampionDetailResponse>

    @GET("api/versions.json")
    suspend fun getVersions(): Response<List<String>>
}
