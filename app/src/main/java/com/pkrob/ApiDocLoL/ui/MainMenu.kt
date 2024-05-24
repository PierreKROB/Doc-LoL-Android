package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.R
import com.pkrob.ApiDocLoL.viewmodel.MainViewModel
import com.airbnb.lottie.compose.*

@Composable
fun MainMenu(navController: NavController, mainViewModel: MainViewModel) {
    val versions by mainViewModel.versions.collectAsState()
    val selectedVersion by mainViewModel.selectedVersion.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

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
                            Color.Black, // Couleur sombre
                            MaterialTheme.colorScheme.primary, // Couleur primaire
                            MaterialTheme.colorScheme.secondary // Couleur secondaire
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Season Dropdown
                    Box(
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    ) {
                        OutlinedButton(
                            onClick = { expandedSeason = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = if (selectedSeason.isEmpty()) "Select Season" else "Season $selectedSeason",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expandedSeason,
                            onDismissRequest = { expandedSeason = false }
                        ) {
                            seasons.forEach { season ->
                                DropdownMenuItem(
                                    text = { Text(text = "Season $season") },
                                    onClick = { onSeasonSelected(season) }
                                )
                            }
                        }
                    }

                    // Version Dropdown
                    if (selectedSeason.isNotEmpty()) {
                        Box(
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        ) {
                            OutlinedButton(
                                onClick = { expandedVersion = true },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(
                                    text = selectedVersion ?: "Select Version",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                            DropdownMenu(
                                expanded = expandedVersion,
                                onDismissRequest = { expandedVersion = false }
                            ) {
                                seasonVersions.forEach { version ->
                                    DropdownMenuItem(
                                        text = { Text(text = version) },
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

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CardButton(
                        navController = navController,
                        text = "Champions",
                        imageRes = R.drawable.epee,
                        destination = "championList"
                    )

                    CardButton(
                        navController = navController,
                        text = "Items",
                        imageRes = R.drawable.po,
                        destination = "itemList"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardButton(navController: NavController, text: String, imageRes: Int, destination: String) {
    Card(
        onClick = { navController.navigate(destination) },
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 18.sp,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black, // Couleur sombre
                        MaterialTheme.colorScheme.primary, // Couleur primaire
                        MaterialTheme.colorScheme.secondary // Couleur secondaire
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(150.dp)
        )
    }
}
