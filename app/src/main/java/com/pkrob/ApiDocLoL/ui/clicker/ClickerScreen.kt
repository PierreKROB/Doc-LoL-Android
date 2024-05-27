package com.pkrob.ApiDocLoL.ui.clicker

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pkrob.ApiDocLoL.viewmodel.ClickerViewModel
import com.pkrob.ApiDocLoL.viewmodel.ClickerViewModelFactory

@Composable
fun ClickerScreen(navController: NavController, viewModel: ClickerViewModel = viewModel(factory = ClickerViewModelFactory(navController.context.applicationContext as Application))) {
    val clickCount by viewModel.clickCount.collectAsState()

    val sharedPreferences = navController.context.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "Utilisateur")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bonjour, $username !")
        Text(text = "Clicks: $clickCount")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onButtonClick() }
        ) {
            Text(text = "Click Me!")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Menu(navController)
    }
}

@Composable
fun Menu(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("upgrade") }
        ) {
            Text(text = "Upgrade")
        }
        Button(
            onClick = { navController.navigate("shop") }
        ) {
            Text(text = "Boutique")
        }
        Button(
            onClick = {
                val sharedPreferences = navController.context.getSharedPreferences("clicker_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().apply()
                navController.navigate("mainMenu") {
                    popUpTo("mainMenu") { inclusive = true }
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Déconnexion")
        }
    }
}
