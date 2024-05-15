package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.pkrob.ApiDocLoL.model.Champion

@Composable
fun ChampionList(champions: List<Champion>, version: String, onChampionClick: (Champion) -> Unit) {
    var query by remember { mutableStateOf("") }
    val filteredChampions = champions.filter {
        it.name.contains(query, ignoreCase = true) || it.title.contains(query, ignoreCase = true)
    }

    Column {
        SearchBar(query = query, onQueryChanged = { query = it }, label = "Recherche Champions")
        LazyColumn {
            items(filteredChampions) { champion ->
                ChampionItem(champion = champion, version = version, onClick = { onChampionClick(champion) })
            }
        }
    }
}
