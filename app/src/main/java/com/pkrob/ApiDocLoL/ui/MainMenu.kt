package com.pkrob.ApiDocLoL.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.viewmodel.MainViewModel

@Composable
fun MainMenu(navController: NavController, mainViewModel: MainViewModel) {
    val versions by mainViewModel.versions.collectAsState()
    val selectedVersion by mainViewModel.selectedVersion.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingScreen()
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                TextButton(onClick = { expanded = true }) {
                    Text(text = selectedVersion ?: "Select Version")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    versions.forEach { version ->
                        DropdownMenuItem(
                            text = { Text(text = version) },
                            onClick = {
                                mainViewModel.selectVersion(version)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { navController.navigate("championList") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                enabled = selectedVersion != null
            ) {
                Text(text = "Voir les champions")
            }
            Button(
                onClick = { navController.navigate("itemList") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                enabled = selectedVersion != null
            ) {
                Text(text = "Voir les items")
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
