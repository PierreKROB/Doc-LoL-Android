package com.pkrob.ApiDocLoL.ui

import LoadingScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.R
import com.pkrob.ApiDocLoL.ui.theme.*
import com.pkrob.ApiDocLoL.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun MainMenu(navController: NavController, mainViewModel: MainViewModel) {
    val versions by mainViewModel.versions.collectAsState()
    val selectedVersion by mainViewModel.selectedVersion.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    // Animations
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    // Filter out the "lolpatch_" versions
    val filteredVersions = versions.filterNot { it.startsWith("lolpatch_") }

    // Extract the list of seasons from the filtered versions
    val seasons = filteredVersions.map { it.split(".")[0] }.distinct()

    // State for selected season
    var selectedSeason by rememberSaveable { mutableStateOf("") }
    var expandedSeason by remember { mutableStateOf(false) }
    var expandedVersion by remember { mutableStateOf(false) }

    // Filter versions based on selected season
    var seasonVersions by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(versions) {
        if (seasons.isNotEmpty() && selectedSeason.isEmpty()) {
            selectedSeason = seasons.first()
        }
    }

    LaunchedEffect(selectedSeason) {
        seasonVersions = filteredVersions.filter { it.startsWith(selectedSeason) }
        if (seasonVersions.isNotEmpty() && selectedVersion.isNullOrEmpty()) {
            mainViewModel.selectVersion(seasonVersions.first())
        }
    }

    fun onSeasonSelected(season: String) {
        selectedSeason = season
        seasonVersions = filteredVersions.filter { it.startsWith(season) }
        if (seasonVersions.isNotEmpty()) {
            mainViewModel.selectVersion(seasonVersions.first())
        }
        expandedSeason = false
    }

    if (isLoading) {
        LoadingScreen()
    } else {
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
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
                    animationSpec = tween(1000),
                    initialOffsetY = { it / 2 }
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Title
                    Text(
                        text = "League of Legends",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = GoldPrimary
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Documentation Explorer",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextSecondaryDark,
                            fontSize = 16.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    // Version Selection Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = SurfaceDark
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Sélection de version",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Season Selector
                                Box(modifier = Modifier.weight(1f)) {
                                    OutlinedButton(
                                        onClick = { expandedSeason = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = BlueAccent,
                                            containerColor = Color.Transparent
                                        ),
                                        border = ButtonDefaults.outlinedButtonBorder.copy(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(BlueAccent, GoldAccent)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = if (selectedSeason.isEmpty()) "Saison" else "S$selectedSeason",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expandedSeason,
                                        onDismissRequest = { expandedSeason = false },
                                        modifier = Modifier.background(SurfaceDark)
                                    ) {
                                        seasons.forEach { season ->
                                            DropdownMenuItem(
                                                text = { 
                                                    Text(
                                                        text = "Saison $season",
                                                        color = TextPrimaryDark
                                                    )
                                                },
                                                onClick = { onSeasonSelected(season) }
                                            )
                                        }
                                    }
                                }

                                // Version Selector
                                if (selectedSeason.isNotEmpty()) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        OutlinedButton(
                                            onClick = { expandedVersion = true },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = BlueAccent,
                                                containerColor = Color.Transparent
                                            ),
                                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(BlueAccent, GoldAccent)
                                                )
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = selectedVersion ?: "Version",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                maxLines = 1
                                            )
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown,
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        DropdownMenu(
                                            expanded = expandedVersion,
                                            onDismissRequest = { expandedVersion = false },
                                            modifier = Modifier.background(SurfaceDark)
                                        ) {
                                            seasonVersions.forEach { version ->
                                                DropdownMenuItem(
                                                    text = { 
                                                        Text(
                                                            text = version,
                                                            color = TextPrimaryDark
                                                        )
                                                    },
                                                    onClick = {
                                                        mainViewModel.selectVersion(version)
                                                        expandedVersion = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Main Navigation Cards
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EnhancedCardButton(
                            text = "Champions",
                            description = "Explorez tous les champions",
                            imageRes = R.drawable.epee,
                            destination = "championList",
                            navController = navController,
                            gradientColors = listOf(BluePrimary, BlueAccent),
                            delay = 0
                        )

                        EnhancedCardButton(
                            text = "Objets",
                            description = "Découvrez les objets du jeu",
                            imageRes = R.drawable.po,
                            destination = "itemList",
                            navController = navController,
                            gradientColors = listOf(GoldPrimary, GoldAccent),
                            delay = 100
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedCardButton(
    text: String,
    description: String,
    imageRes: Int,
    destination: String,
    navController: NavController,
    gradientColors: List<Color>,
    delay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
            animationSpec = tween(600),
            initialOffsetY = { it / 3 }
        )
    ) {
        Card(
            onClick = {
                navController.navigate(destination)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .scale(scale)
                .graphicsLayer {
                    shadowElevation = if (isPressed) 4.dp.toPx() else 12.dp.toPx()
                },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(gradientColors),
                        RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        )
                    }
                    
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .graphicsLayer {
                                scaleX = if (isPressed) 1.1f else 1f
                                scaleY = if (isPressed) 1.1f else 1f
                            }
                    )
                }
            }
        }
    }
}
