package com.pkrob.ApiDocLoL.model

import com.google.gson.annotations.SerializedName

data class ChampionDetailResponse(
    @SerializedName("data")
    val data: Map<String, ChampionDetail>
)

data class ChampionDetail(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("lore")
    val lore: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("info")
    val info: ChampionInfo,
    @SerializedName("stats")
    val stats: ChampionStats,
    @SerializedName("spells")
    val spells: List<ChampionSpell>,
    @SerializedName("passive")
    val passive: ChampionPassive,
    @SerializedName("image")
    val image: ChampionImage,
    @SerializedName("skins")
    val skins: List<ChampionSkin>
)

data class ChampionInfo(
    @SerializedName("attack")
    val attack: Double,
    @SerializedName("defense")
    val defense: Double,
    @SerializedName("magic")
    val magic: Double,
    @SerializedName("difficulty")
    val difficulty: Double
)

data class ChampionStats(
    @SerializedName("hp")
    val hp: Double,
    @SerializedName("hpperlevel")
    val hpPerLevel: Double,
    @SerializedName("mp")
    val mp: Double,
    @SerializedName("mpperlevel")
    val mpPerLevel: Double,
    @SerializedName("movespeed")
    val moveSpeed: Double,
    @SerializedName("armor")
    val armor: Double,
    @SerializedName("armorperlevel")
    val armorPerLevel: Double,
    @SerializedName("spellblock")
    val spellBlock: Double,
    @SerializedName("spellblockperlevel")
    val spellBlockPerLevel: Double,
    @SerializedName("attackrange")
    val attackRange: Double,
    @SerializedName("hpregen")
    val hpRegen: Double,
    @SerializedName("hpregenperlevel")
    val hpRegenPerLevel: Double,
    @SerializedName("mpregen")
    val mpRegen: Double,
    @SerializedName("mpregenperlevel")
    val mpRegenPerLevel: Double,
    @SerializedName("crit")
    val crit: Double,
    @SerializedName("critperlevel")
    val critPerLevel: Double,
    @SerializedName("attackdamage")
    val attackDamage: Double,
    @SerializedName("attackdamageperlevel")
    val attackDamagePerLevel: Double,
    @SerializedName("attackspeedperlevel")
    val attackSpeedPerLevel: Double,
    @SerializedName("attackspeed")
    val attackSpeed: Double
)

data class ChampionSpell(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("tooltip")
    val tooltip: String,
    @SerializedName("cooldown")
    val cooldown: List<Double>,
    @SerializedName("cost")
    val cost: List<Double>,
    @SerializedName("range")
    val range: List<Double>,
    @SerializedName("image")
    val image: ChampionImage
)

data class ChampionPassive(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: ChampionImage
)

data class ChampionSkin(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("num")
    val num: Double,
    @SerializedName("chromas")
    val chromas: Boolean
)

data class ChampionImage(
    @SerializedName("full")
    val full: String
)
