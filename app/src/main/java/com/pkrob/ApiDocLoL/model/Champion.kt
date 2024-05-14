package com.pkrob.ApiDocLoL.model

import com.google.gson.annotations.SerializedName

data class ChampionsResponse(
    @SerializedName("data")
    val data: Map<String, Champion>
)

data class Champion(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("blurb")
    val blurb: String
)
