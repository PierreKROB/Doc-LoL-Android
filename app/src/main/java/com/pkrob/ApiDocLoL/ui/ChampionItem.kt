package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.pkrob.ApiDocLoL.model.Champion

@Composable
fun ChampionItem(champion: Champion, version: String, onClick: () -> Unit) {
    Row(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/champion/${champion.id}.png"
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Champion Image",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = champion.name, style = MaterialTheme.typography.displayMedium)
            Text(text = champion.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
