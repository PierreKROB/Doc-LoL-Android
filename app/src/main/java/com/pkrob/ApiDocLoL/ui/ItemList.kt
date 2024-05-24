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
    "FlatHPPoolMod" to "Santé",
    "rFlatHPModPerLevel" to "Santé par Niveau",
    "FlatMPPoolMod" to "Mana",
    "rFlatMPModPerLevel" to "Mana par Niveau",
    "PercentHPPoolMod" to "Pourcentage de Santé",
    "PercentMPPoolMod" to "Pourcentage de Mana",
    "FlatHPRegenMod" to "Régénération de Santé",
    "rFlatHPRegenModPerLevel" to "Régénération de Santé par Niveau",
    "PercentHPRegenMod" to "Pourcentage de Régénération de Santé",
    "FlatMPRegenMod" to "Régénération de Mana",
    "rFlatMPRegenModPerLevel" to "Régénération de Mana par Niveau",
    "PercentMPRegenMod" to "Pourcentage de Régénération de Mana",
    "FlatArmorMod" to "Armure",
    "rFlatArmorModPerLevel" to "Armure par Niveau",
    "PercentArmorMod" to "Pourcentage d'Armure",
    "rFlatArmorPenetrationMod" to "Pénétration d'Armure",
    "rFlatArmorPenetrationModPerLevel" to "Pénétration d'Armure par Niveau",
    "rPercentArmorPenetrationMod" to "Pourcentage de Pénétration d'Armure",
    "rPercentArmorPenetrationModPerLevel" to "Pourcentage de Pénétration d'Armure par Niveau",
    "FlatPhysicalDamageMod" to "Dégâts Physiques",
    "rFlatPhysicalDamageModPerLevel" to "Dégâts Physiques par Niveau",
    "PercentPhysicalDamageMod" to "Pourcentage de Dégâts Physiques",
    "FlatMagicDamageMod" to "Dégâts Magiques",
    "rFlatMagicDamageModPerLevel" to "Dégâts Magiques par Niveau",
    "PercentMagicDamageMod" to "Pourcentage de Dégâts Magiques",
    "FlatMovementSpeedMod" to "Vitesse de Déplacement",
    "rFlatMovementSpeedModPerLevel" to "Vitesse de Déplacement par Niveau",
    "PercentMovementSpeedMod" to "Pourcentage de Vitesse de Déplacement",
    "rPercentMovementSpeedModPerLevel" to "Pourcentage de Vitesse de Déplacement par Niveau",
    "FlatAttackSpeedMod" to "Vitesse d'Attaque",
    "PercentAttackSpeedMod" to "Pourcentage de Vitesse d'Attaque",
    "rPercentAttackSpeedModPerLevel" to "Pourcentage de Vitesse d'Attaque par Niveau",
    "rFlatDodgeMod" to "Esquive",
    "rFlatDodgeModPerLevel" to "Esquive par Niveau",
    "PercentDodgeMod" to "Pourcentage d'Esquive",
    "FlatCritChanceMod" to "Chance Critique",
    "rFlatCritChanceModPerLevel" to "Chance Critique par Niveau",
    "PercentCritChanceMod" to "Pourcentage de Chance Critique",
    "FlatCritDamageMod" to "Dégâts Critiques",
    "rFlatCritDamageModPerLevel" to "Dégâts Critiques par Niveau",
    "PercentCritDamageMod" to "Pourcentage de Dégâts Critiques",
    "FlatBlockMod" to "Blocage",
    "PercentBlockMod" to "Pourcentage de Blocage",
    "FlatSpellBlockMod" to "Résistance Magique",
    "rFlatSpellBlockModPerLevel" to "Résistance Magique par Niveau",
    "PercentSpellBlockMod" to "Pourcentage de Résistance Magique",
    "FlatEXPBonus" to "Bonus d'EXP",
    "PercentEXPBonus" to "Pourcentage de Bonus d'EXP",
    "rPercentCooldownMod" to "Réduction des Temps de Recharge",
    "rPercentCooldownModPerLevel" to "Réduction des Temps de Recharge par Niveau",
    "rFlatTimeDeadMod" to "Temps de Mort Réduit",
    "rFlatTimeDeadModPerLevel" to "Temps de Mort Réduit par Niveau",
    "rPercentTimeDeadMod" to "Pourcentage de Temps de Mort Réduit",
    "rPercentTimeDeadModPerLevel" to "Pourcentage de Temps de Mort Réduit par Niveau",
    "rFlatGoldPer10Mod" to "Or par 10 secondes",
    "rFlatMagicPenetrationMod" to "Pénétration Magique",
    "rFlatMagicPenetrationModPerLevel" to "Pénétration Magique par Niveau",
    "rPercentMagicPenetrationMod" to "Pourcentage de Pénétration Magique",
    "rPercentMagicPenetrationModPerLevel" to "Pourcentage de Pénétration Magique par Niveau",
    "FlatEnergyRegenMod" to "Régénération d'Énergie",
    "rFlatEnergyRegenModPerLevel" to "Régénération d'Énergie par Niveau",
    "FlatEnergyPoolMod" to "Énergie",
    "rFlatEnergyModPerLevel" to "Énergie par Niveau",
    "PercentLifeStealMod" to "Vol de Vie",
    "PercentSpellVampMod" to "Vampirisme des Sorts"
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
