package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pkrob.ApiDocLoL.model.Champion

@Composable
fun ChampionList(champions: List<Champion>, version: String, onChampionClick: (Champion) -> Unit) {
    var query by remember { mutableStateOf("") }
    val filteredChampions = champions.filter {
        it.name.contains(query, ignoreCase = true) || it.title.contains(query, ignoreCase = true)
    }

    Column {
        SearchBar(query = query, onQueryChanged = { query = it })
        LazyColumn {
            items(filteredChampions) { champion ->
                ChampionItem(champion = champion, version = version, onClick = { onChampionClick(champion) })
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(text = "Recherche Champions") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
