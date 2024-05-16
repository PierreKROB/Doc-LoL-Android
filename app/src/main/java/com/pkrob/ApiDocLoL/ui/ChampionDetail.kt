package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.pkrob.ApiDocLoL.viewmodel.ChampionViewModel

@Composable
fun ChampionDetailScreen(viewModel: ChampionViewModel, version: String, championId: String) {
    viewModel.getChampionDetail(version, championId)
    val championDetail by viewModel.championDetail.collectAsState()

    championDetail?.let { champion ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/champion/${champion.id}.png"
                        Image(
                            painter = rememberImagePainter(imageUrl),
                            contentDescription = "Champion Image",
                            modifier = Modifier
                                .size(128.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp))
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = champion.name,
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = champion.title,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
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
                }
            }

            item {
                Text(
                    text = "Spells",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            items(champion.spells) { spell ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val spellImageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/spell/${spell.image.full}"
                        Image(
                            painter = rememberAsyncImagePainter(spellImageUrl),
                            contentDescription = "Spell Image",
                            modifier = Modifier
                                .size(64.dp)
                                .shadow(4.dp, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = spell.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = spell.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
