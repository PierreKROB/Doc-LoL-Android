package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.pkrob.ApiDocLoL.viewmodel.ChampionViewModel

@Composable
fun ChampionDetailScreen(viewModel: ChampionViewModel, version: String, championId: String) {
    viewModel.getChampionDetail(version, championId)
    val championDetail by viewModel.championDetail.collectAsState()

    championDetail?.let { champion ->
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/champion/${champion.id}.png"
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Champion Image",
                    modifier = Modifier.size(128.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = champion.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = champion.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = champion.lore,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Tags: ${champion.tags.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                Text(
                    text = "Spells",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(champion.spells) { spell ->
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    val spellImageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/spell/${spell.image.full}"
                    Image(
                        painter = rememberImagePainter(spellImageUrl),
                        contentDescription = "Spell Image",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(text = spell.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = spell.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
