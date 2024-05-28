package com.pkrob.ApiDocLoL.model

data class ChampionUpgrade(
    val name: String,
    val description: String,
    val cost: Int,
    val statIncrease: Int,
    val statType: String,
    val rarity: String,
    val level: Int,
    val skins: List<Skin>
)

data class Skin(
    val name: String,
    val rarityIncrease: String,
    val statIncrease: Int,
    val imageUrl: String
)
