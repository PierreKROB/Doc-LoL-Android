package com.pkrob.ApiDocLoL.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pkrob.ApiDocLoL.model.Item
import com.pkrob.ApiDocLoL.ui.theme.*

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
                        text = "Objets",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 28.sp
                        )
                    )
                    Text(
                        text = "${filteredItems.size} objets disponibles",
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
                label = "Rechercher un objet...",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Items List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredItems) { item ->
                    EnhancedItemRow(item = item, version = version)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedItemRow(item: Item, version: String) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    val imageUrl = remember(version, item.image.full) {
        "https://ddragon.leagueoflegends.com/cdn/$version/img/item/${item.image.full}"
    }
    val painter = rememberAsyncImagePainter(imageUrl)
    val imageState = painter.state

    Card(
        onClick = { setShowDialog(true) },
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Item Image
            Box(
                modifier = Modifier.size(64.dp)
            ) {
                when (imageState) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            SurfaceVariant,
                                            SurfaceDark
                                        )
                                    ),
                                    RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = GoldAccent
                            )
                        }
                    }
                    is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SurfaceVariant, RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "?",
                                color = TextSecondaryDark,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    else -> {
                        Image(
                            painter = painter,
                            contentDescription = "Item ${item.name}",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Item Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryDark,
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${item.gold.total} PO",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GoldAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                
                if (item.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item.tags.take(2).forEach { tag ->
                            Surface(
                                color = BlueAccent.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = tag,
                                    color = BlueAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Arrow indicator
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Voir détails",
                tint = TextSecondaryDark,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (showDialog) {
        EnhancedItemDetailsDialog(item = item, version = version, onDismiss = { setShowDialog(false) })
    }
}

@Composable
fun EnhancedItemDetailsDialog(item: Item, version: String, onDismiss: () -> Unit) {
    val imageUrl = remember(version, item.image.full) {
        "https://ddragon.leagueoflegends.com/cdn/$version/img/item/${item.image.full}"
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Détails de l'objet",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary
                        )
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = TextSecondaryDark
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Item image and name
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Item Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryDark
                                )
                            )
                            
                            Text(
                                text = "${item.gold.total} PO",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldAccent,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Description
                    if (item.description.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Description",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlueAccent
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextPrimaryDark
                                    )
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // Stats
                    if (item.stats.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Statistiques",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlueAccent
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                item.stats.forEach { (key, value) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = statNameMapping[key] ?: key,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = TextPrimaryDark
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "+$value",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = SuccessColor,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // Tags
                    if (item.tags.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Catégories",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlueAccent
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    item.tags.forEach { tag ->
                                        Surface(
                                            color = GoldAccent.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = tag,
                                                color = GoldAccent,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Keep these for compatibility
@Composable
fun ItemRow(item: Item, version: String) {
    EnhancedItemRow(item = item, version = version)
}

@Composable
fun ItemDetailsDialog(item: Item, version: String, onDismiss: () -> Unit) {
    EnhancedItemDetailsDialog(item = item, version = version, onDismiss = onDismiss)
}
