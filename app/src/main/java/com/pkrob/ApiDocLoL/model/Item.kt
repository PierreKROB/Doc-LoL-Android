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
    val image: ItemImage
)

data class ItemImage(
    @SerializedName("full")
    val full: String
)
