package com.pkrob.ApiDocLoL.model

data class User(
    val userId: String,
    val upgrades: List<ChampionUpgrade> = emptyList(),
    val artifacts: List<Artifact> = emptyList(),
    val PO: Int = 0
)
