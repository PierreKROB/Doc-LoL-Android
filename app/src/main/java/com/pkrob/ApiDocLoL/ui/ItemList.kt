package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.pkrob.ApiDocLoL.model.Item

@Composable
fun ItemList(items: List<Item>, version: String) {
    LazyColumn {
        items(items) { item ->
            ItemRow(item = item, version = version)
        }
    }
}

@Composable
fun ItemRow(item: Item, version: String) {
    Row(modifier = Modifier.padding(8.dp)) {
        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/item/${item.image.full}"
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Item Image",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
