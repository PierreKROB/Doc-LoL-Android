package com.pkrob.ApiDocLoL.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("data")
    val data: Map<String, Item>
)

data class Item(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: ItemImage,
    @SerializedName("gold")
    val gold: ItemGold,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("stats")
    val stats: Map<String, Double>
)

data class ItemImage(
    @SerializedName("full")
    val full: String
)

data class ItemGold(
    @SerializedName("base")
    val base: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("sell")
    val sell: Int,
    @SerializedName("purchasable")
    val purchasable: Boolean
)
