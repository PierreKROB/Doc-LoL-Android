package com.pkrob.ApiDocLoL.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pkrob.ApiDocLoL.ui.theme.*
import com.pkrob.ApiDocLoL.viewmodel.ChampionViewModel
import kotlinx.coroutines.delay

@Composable
fun ChampionDetailScreen(viewModel: ChampionViewModel, version: String, championId: String) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        viewModel.getChampionDetail(version, championId)
        delay(200)
        isVisible = true
    }
    
    val championDetail by viewModel.championDetail.collectAsState()
    val error by viewModel.error.collectAsState()

    if (error != null) {
        EnhancedErrorDialog(error = error, onDismiss = { viewModel.clearError() })
    } else {
        championDetail?.let { champion ->
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Champion Header Card
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Champion Image avec animation CONDITIONNELLE
                                val championImageUrl = remember(version, champion.id) {
                                    "https://ddragon.leagueoflegends.com/cdn/$version/img/champion/${champion.id}.png"
                                }
                                val painter = rememberAsyncImagePainter(championImageUrl)
                                
                                Box(
                                    modifier = Modifier.size(140.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    when (painter.state) {
                                        is AsyncImagePainter.State.Loading -> {
                                            // Animation SEULEMENT pendant loading
                                            val infiniteTransition = rememberInfiniteTransition(label = "border")
                                            val borderRotation by infiniteTransition.animateFloat(
                                                initialValue = 0f,
                                                targetValue = 360f,
                                                animationSpec = infiniteRepeatable(
                                                    animation = tween(6000, easing = LinearEasing),
                                                    repeatMode = RepeatMode.Restart
                                                ),
                                                label = "rotation"
                                            )
                                            
                                            // Bordure animée
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .graphicsLayer { rotationZ = borderRotation }
                                                    .background(
                                                        Brush.sweepGradient(
                                                            colors = listOf(
                                                                GoldPrimary,
                                                                BlueAccent,
                                                                GoldPrimary,
                                                                Color.Transparent,
                                                                Color.Transparent
                                                            )
                                                        ),
                                                        CircleShape
                                                    )
                                            )
                                            
                                            // Fond au centre
                                            Box(
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .background(SurfaceDark, CircleShape)
                                            )
                                        }
                                        is AsyncImagePainter.State.Error -> {
                                            Box(
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .background(SurfaceVariant, CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = champion.name.firstOrNull()?.toString()?.uppercase() ?: "?",
                                                    color = GoldAccent,
                                                    fontSize = 32.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        is AsyncImagePainter.State.Success -> {
                                            // Image chargée - PAS d'animation
                                            Image(
                                                painter = painter,
                                                contentDescription = "Champion ${champion.name}",
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        else -> {
                                            // État initial
                                            Box(
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .background(SurfaceVariant, CircleShape)
                                            )
                                        }
                                    }
                                
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Text(
                                    text = champion.name,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary,
                                        fontSize = 28.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                
                                Text(
                                    text = champion.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = BlueAccent,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                
                                // Tags
                                if (champion.tags.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    ) {
                                        champion.tags.forEach { tag ->
                                            Surface(
                                                color = GoldAccent.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(12.dp)
                                            ) {
                                                Text(
                                                    text = tag,
                                                    color = GoldAccent,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Lore
                    if (champion.lore.isNotEmpty()) {
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Text(
                                        text = "Histoire",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = GoldPrimary
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = champion.lore,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextPrimaryDark,
                                            lineHeight = 22.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Passif
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Text(
                                    text = "Passif",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BlueAccent
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val passiveImageUrl = remember(version, champion.passive.image.full) {
                                    "https://ddragon.leagueoflegends.com/cdn/$version/img/passive/${champion.passive.image.full}"
                                    }
                                    SpellImageWithLoader(
                                        imageUrl = passiveImageUrl,
                                    spellName = champion.passive.name,
                                    modifier = Modifier.size(64.dp)
                                )
                                    
                                    Spacer(modifier = Modifier.width(16.dp))
                                    
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = champion.passive.name,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextPrimaryDark
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = champion.passive.description,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = TextSecondaryDark
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Sorts
                    item {
                        Text(
                            text = "Sorts (${champion.spells.size})",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(champion.spells) { spell ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SpellImageWithLoader(
                                    imageUrl = "https://ddragon.leagueoflegends.com/cdn/$version/img/spell/${spell.image.full}",
                                    spellName = spell.name,
                                    modifier = Modifier.size(64.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = spell.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimaryDark
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = spell.description,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextSecondaryDark
                                        )
                                    )
                                    
                                    if (spell.cooldown.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Cooldown: ${spell.cooldown.joinToString("/")}s",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = BlueAccent,
                                                fontWeight = FontWeight.Medium
                                            )
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

@Composable
fun SpellImageWithLoader(
    imageUrl: String,
    spellName: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            // Éviter les recompositions inutiles
        }
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SurfaceVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = BlueAccent,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SurfaceVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        color = BlueAccent,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            else -> {
                Image(
                    painter = painter,
                    contentDescription = "Spell $spellName",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun EnhancedErrorDialog(error: String?, onDismiss: () -> Unit) {
    if (error != null) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Erreur",
                        tint = ErrorColor,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Erreur",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ErrorColor
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextPrimaryDark
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "OK",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// Compatibility
@Composable
fun ErrorDialog(error: String?, onDismiss: () -> Unit) {
    EnhancedErrorDialog(error = error, onDismiss = onDismiss)
}
