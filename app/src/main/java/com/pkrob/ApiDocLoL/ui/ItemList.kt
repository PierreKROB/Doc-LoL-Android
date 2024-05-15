package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pkrob.ApiDocLoL.model.Item

@Composable
fun ItemList(items: List<Item>, version: String) {
    var query by remember { mutableStateOf("") }
    val filteredItems = items.filter {
        it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    Column {
        SearchBar(query = query, onQueryChanged = { query = it }, label = "Recherche Items")
        LazyColumn {
            items(filteredItems) { item ->
                ItemRow(item = item, version = version)
            }
        }
    }
}

@Composable
fun ItemRow(item: Item, version: String) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { setShowDialog(true) }
    ) {
        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/item/${item.image.full}"
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Item Image",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.displayMedium)
        }
    }

    if (showDialog) {
        ItemDetailsDialog(item = item, version = version, onDismiss = { setShowDialog(false) })
    }
}

@Composable
fun ItemDetailsDialog(item: Item, version: String, onDismiss: () -> Unit) {
    val imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/item/${item.image.full}"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = item.name) },
        text = {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Item Image",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = item.description)
                Text(text = "Coût : ${item.gold.total} PO", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Statistiques :", style = MaterialTheme.typography.bodyMedium)
                item.stats.flatCritChanceMod?.let {
                    Text(text = "Chance Critique : ${it * 100}%", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatPhysicalDamageMod?.let {
                    Text(text = "Dégâts Physiques : $it", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatHPPoolMod?.let {
                    Text(text = "Santé : $it", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatMagicDamageMod?.let {
                    Text(text = "Dégâts Magiques : $it", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.percentAttackSpeedMod?.let {
                    Text(text = "Vitesse d'Attaque : ${it * 100}%", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatArmorMod?.let {
                    Text(text = "Armure : $it", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatSpellBlockMod?.let {
                    Text(text = "Résistance Magique : $it", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.percentLifeStealMod?.let {
                    Text(text = "Vol de Vie : ${it * 100}%", style = MaterialTheme.typography.bodyMedium)
                }
                item.stats.flatMovementSpeedMod?.let {
                    Text(text = "Vitesse de Déplacement : $it", style = MaterialTheme.typography.bodyMedium)
                }
                Text(text = "Tags : ${item.tags.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer")
            }
        }
    )
}
