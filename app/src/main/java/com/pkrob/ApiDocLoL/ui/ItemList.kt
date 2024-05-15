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

val statNameMapping = mapOf(
    "FlatCritChanceMod" to "Chance Critique",
    "FlatPhysicalDamageMod" to "Dégâts Physiques",
    "FlatHPPoolMod" to "Santé",
    "FlatMagicDamageMod" to "Dégâts Magiques",
    "PercentAttackSpeedMod" to "Vitesse d'Attaque",
    "FlatArmorMod" to "Armure",
    "FlatSpellBlockMod" to "Résistance Magique",
    "PercentLifeStealMod" to "Vol de Vie",
    "FlatMovementSpeedMod" to "Vitesse de Déplacement"
)

@Composable
fun ItemList(items: List<Item>, version: String) {
    var query by remember { mutableStateOf("") }
    val filteredItems = items.filter {
        it.name.contains(query, ignoreCase = true)
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
                item.stats.forEach { (key, value) ->
                    Text(text = "${statNameMapping[key] ?: key} : $value", style = MaterialTheme.typography.bodyMedium)
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
