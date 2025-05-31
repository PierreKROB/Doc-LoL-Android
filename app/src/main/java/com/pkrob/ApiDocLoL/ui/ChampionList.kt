package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pkrob.ApiDocLoL.model.Champion
import com.pkrob.ApiDocLoL.ui.theme.*

@Composable
fun ChampionList(champions: List<Champion>, version: String, onChampionClick: (Champion) -> Unit) {
    var query by remember { mutableStateOf("") }
    val filteredChampions = champions.filter {
        it.name.contains(query, ignoreCase = true) || it.title.contains(query, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientMiddle,
                        GradientEnd
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Champions",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 28.sp
                        )
                    )
                    Text(
                        text = "${filteredChampions.size} champions disponibles",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondaryDark
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Search Bar
            EnhancedSearchBar(
                query = query,
                onQueryChanged = { query = it },
                label = "Rechercher un champion...",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Champions List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredChampions) { champion ->
                    EnhancedChampionItem(
                        champion = champion,
                        version = version,
                        onClick = { onChampionClick(champion) }
                    )
                }
            }
        }
    }
}
